package com.jonduran.circleci.project.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.jonduran.circleci.cache.entity.SourceControl
import com.jonduran.circleci.data.project.ProjectRepository
import com.jonduran.circleci.extensions.combineLatest

class ProjectListViewModel @ViewModelInject constructor(
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
}