package com.jonduran.circleci.project.list

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.savedstate.SavedStateRegistryOwner
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.data.ProjectRepository
import com.jonduran.circleci.extensions.combineLatest

class ProjectListViewModel(
    handle: SavedStateHandle,
    private val projectRepository: ProjectRepository
) : ViewModel() {

    val versionControl = handle.getLiveData("vcs", SourceControl.values())

    val organization = handle.getLiveData("org", "%%")

    val state = versionControl
        .combineLatest(organization) { vcs, org -> vcs to org }
        .switchMap { (vcs, org) -> getProjectsLiveData(vcs, org) }

    private fun getProjectsLiveData(vcs: Array<SourceControl>, org: String) = liveData {
        emit(ProjectListComponent.State.Loading)
        try {
            val projects = projectRepository.getListOfProjects(vcs, org).map { entity ->
                ProjectItem(
                    name = entity.name,
                    username = entity.username
                )
            }
            emit(ProjectListComponent.State.Success(projects))
        } catch (e: Exception) {
            emit(ProjectListComponent.State.Failure(e))
        }
    }

    class Factory(
        private val projectRepository: ProjectRepository,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ProjectListViewModel(handle, projectRepository) as T
        }
    }
}