package com.jonduran.circleci

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.jonduran.circleci.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MainViewModel(
    private val handle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    val state: Flow<State> = flow {
        if (handle.contains("savedInstance")) {
            emit(State.SavedInstance)
        } else {
            emit(State.Loading)
            handle.set("savedInstance", Bundle())
            if (userRepository.hasCredentials()) {
                emit(State.Success)
            } else {
                emit(State.Unauthorized)
            }
        }
    }.catch { e ->
        emit(State.Failure(e))
    }

    class Factory(
        private val repository: UserRepository,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs)  {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return MainViewModel(handle, repository) as T
        }
    }

    sealed class State {
        object Loading : State()
        object SavedInstance : State()
        object Success : State()
        object Unauthorized : State()
        data class Failure(val error: Throwable) : State()
    }
}