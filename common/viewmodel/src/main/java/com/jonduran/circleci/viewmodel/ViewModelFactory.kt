package com.jonduran.circleci.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject

typealias ViewModelFactoryMap = Map<Class<out ViewModel>, ViewModelAssistedFactory<out ViewModel>>

class ViewModelFactory @Inject constructor(
    private val map: @JvmSuppressWildcards ViewModelFactoryMap,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val factory = map[modelClass]
        checkNotNull(factory) { "Unknown ViewModel class: ${modelClass.name}" }
        @Suppress("UNCHECKED_CAST")
        return factory.create(handle) as T
    }
}