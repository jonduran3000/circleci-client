package com.jonduran.circleci.project.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.jonduran.circleci.cache.entity.SourceControl
import com.jonduran.circleci.data.project.ProjectRepository
import com.jonduran.circleci.extensions.combineLatest
import com.jonduran.circleci.viewmodel.AssistedProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class ProjectListViewModel @AssistedInject constructor(
    @Assisted savedState: SavedStateHandle,
    private val repository: ProjectRepository
) : ViewModel() {

    val versionControl = savedState.getLiveData("vcs", SourceControl.values())

    val organization = savedState.getLiveData("org", "%%")

    val state = versionControl
        .combineLatest(organization) { vcs, org -> vcs to org }
        .switchMap { (vcs, org) -> getProjectsLiveData(vcs, org) }

    private fun getProjectsLiveData(vcs: Array<SourceControl>, org: String) = liveData {
        emit(State.Loading)
        try {
            val projects = repository.getListOfProjects(vcs, org).map { entity ->
                ProjectItem(
                    name = entity.name,
                    username = entity.username
                )
            }
            emit(State.Success(projects))
        } catch (e: Exception) {
            emit(State.Failure(e))
        }
    }

    @AssistedInject.Factory
    interface Provider : AssistedProvider<ProjectListViewModel> {
        override fun provide(savedState: SavedStateHandle): ProjectListViewModel
    }
}