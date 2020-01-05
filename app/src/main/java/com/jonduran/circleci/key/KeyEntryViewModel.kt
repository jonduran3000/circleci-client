package com.jonduran.circleci.key

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.data.UserRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KeyEntryViewModel(private val userRepository: UserRepository) : ViewModel() {
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
                _state.offer(State.EmptyKey)
            } else {
                storeApiKey(key)
            }
        }
    }

    private suspend fun storeApiKey(key: CharSequence) {
        try {
            userRepository.storeKey(key.toString())
            userRepository.getCurrentUser()
            _state.offer(State.Success)
        } catch (e: Exception) {
            checkForUnauthorizedError(e)
        }
    }

    private suspend fun checkForUnauthorizedError(e: Exception) {
        userRepository.deleteKey()
        if (e is HttpException && e.code() == 401) {
            _state.offer(State.InvalidKey)
        } else {
            _state.offer(
                State.Failure(
                    e
                )
            )
        }
    }

    object Factory : ViewModelProvider.Factory {
        private lateinit var repository: UserRepository

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return KeyEntryViewModel(repository) as T
        }

        operator fun invoke(repository: UserRepository): Factory {
            if (!this::repository.isInitialized) {
                Factory.repository = repository
            }
            return this
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
}