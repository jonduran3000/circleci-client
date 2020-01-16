package com.jonduran.circleci.project.list

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.jonduran.circleci.data.ProjectRepository
import javax.inject.Inject
import javax.inject.Named

class ProjectListViewModelFactory @Inject constructor(
    @Named("ProjectList") owner: SavedStateRegistryOwner,
    private val repository: ProjectRepository
) : AbstractSavedStateViewModelFactory(owner, null) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return ProjectListViewModel(handle, repository) as T
    }
}