package com.jonduran.circleci.key

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.jonduran.circleci.data.UserRepository
import javax.inject.Inject
import javax.inject.Named

class KeyEntryViewModelFactory @Inject constructor(
    @Named("KeyEntry") owner: SavedStateRegistryOwner,
    private val repository: UserRepository
) : AbstractSavedStateViewModelFactory(owner, null) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return KeyEntryViewModel(handle, repository) as T
    }
}