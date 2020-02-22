package com.jonduran.circleci.key

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.data.UserRepository
import com.jonduran.circleci.extensions.safeOffer
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class KeyEntryViewModel(
    savedState: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = ConflatedBroadcastChannel<State>()
    val state: Flow<State> = _state.asFlow()

    fun process(action: Action) {
        when (action) {
            is Action.OnSubmitClicked -> handleOnSubmitClicked(action)
        }
    }

    private fun handleOnSubmitClicked(action: Action.OnSubmitClicked) {
        viewModelScope.launch {
            val key = action.key
            if (key.isNullOrEmpty()) {
                _state.safeOffer(State.EmptyKey)
            } else {
                storeApiKey(key)
            }
        }
    }

    private suspend fun storeApiKey(key: CharSequence) {
        try {
            userRepository.storeKey(key.toString())
            userRepository.getCurrentUser()
            _state.safeOffer(State.Success)
        } catch (e: Exception) {
            checkForUnauthorizedError(e)
        }
    }

    private suspend fun checkForUnauthorizedError(e: Exception) {
        userRepository.deleteKey()
        if (e is HttpException && e.code() == 401) {
            _state.safeOffer(State.InvalidKey)
        } else {
            _state.safeOffer(State.Failure(e))
        }
    }

    sealed class Action {
        data class OnSubmitClicked(val key: CharSequence?) : Action()
    }

    sealed class State {
        object EmptyKey : State()
        object InvalidKey : State()
        object Success : State()
        data class Failure(val error: Throwable) : State()
    }

    class Factory @Inject constructor(
        fragment: KeyEntryFragment,
        private val repository: UserRepository
    ) : AbstractSavedStateViewModelFactory(fragment, fragment.arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return KeyEntryViewModel(handle, repository) as T
        }
    }
}