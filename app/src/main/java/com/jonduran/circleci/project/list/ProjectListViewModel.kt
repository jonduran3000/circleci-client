package com.jonduran.circleci.project.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.data.ProjectRepository
import com.jonduran.circleci.extensions.combineLatest

class ProjectListViewModel(
    savedState: SavedStateHandle,
    private val repository: ProjectRepository
) : ViewModel() {

    val versionControl = savedState.getLiveData("vcs", SourceControl.values())

    val organization = savedState.getLiveData("org", "%%")

    val state = versionControl
        .combineLatest(organization) { vcs, org -> vcs to org }
        .switchMap { (vcs, org) -> getProjectsLiveData(vcs, org) }

    private fun getProjectsLiveData(vcs: Array<SourceControl>, org: String) = liveData {
        emit(ProjectListUiComponent.State.Loading)
        try {
            val projects = repository.getListOfProjects(vcs, org).map { entity ->
                ProjectItem(
                    name = entity.name,
                    username = entity.username
                )
            }
            emit(ProjectListUiComponent.State.Success(projects))
        } catch (e: Exception) {
            emit(ProjectListUiComponent.State.Failure(e))
        }
    }
}