package com.jonduran.circleci.project.list

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.data.ProjectRepository
import com.jonduran.circleci.extensions.combineLatest
import javax.inject.Inject

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

    class Factory @Inject constructor(
        fragment: ProjectListFragment,
        private val repository: ProjectRepository
    ) : AbstractSavedStateViewModelFactory(fragment, fragment.arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ProjectListViewModel(handle, repository) as T
        }
    }
}