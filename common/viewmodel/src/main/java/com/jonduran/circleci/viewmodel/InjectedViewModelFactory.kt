package com.jonduran.circleci.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

typealias ProviderMap = Map<Class<out ViewModel>, AssistedProvider<out ViewModel>>

class InjectedViewModelFactory @AssistedInject constructor(
    private val map: @JvmSuppressWildcards ProviderMap,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val provider = map[modelClass]
        checkNotNull(provider) { "Unknown ViewModel class: ${modelClass.name}" }
        @Suppress("UNCHECKED_CAST")
        return provider.provide(handle) as T
    }

    @AssistedInject.Factory
    interface Producer {
        fun produce(
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle?
        ): AbstractSavedStateViewModelFactory
    }
}