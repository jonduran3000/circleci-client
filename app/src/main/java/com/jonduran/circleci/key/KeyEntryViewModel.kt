package com.jonduran.circleci.key

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.data.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KeyEntryViewModel @ViewModelInject constructor(
    @Assisted savedState: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Uninitialized)
    val state: StateFlow<State> get() = _state

    fun process(action: Action) {
        when (action) {
            is Action.OnSubmitClicked -> handleOnSubmitClicked(action)
        }
    }

    private fun handleOnSubmitClicked(action: Action.OnSubmitClicked) {
        viewModelScope.launch {
            val key = action.key
            if (key.isNullOrEmpty()) {
                _state.value = State.EmptyKey
            } else {
                storeApiKey(key)
            }
        }
    }

    private suspend fun storeApiKey(key: CharSequence) {
        try {
            userRepository.storeKey(key.toString())
            userRepository.getCurrentUser()
            _state.value = State.Success
        } catch (e: Exception) {
            checkForUnauthorizedError(e)
        }
    }

    private suspend fun checkForUnauthorizedError(e: Exception) {
        userRepository.deleteKey()
        _state.value = if (e is HttpException && e.code() == 401) {
            State.InvalidKey
        } else {
            State.Failure(e)
        }
    }

    sealed class Action {
        data class OnSubmitClicked(val key: CharSequence?) : Action()
    }

    sealed class State {
        object Uninitialized : State()
        object EmptyKey : State()
        object InvalidKey : State()
        object Success : State()
        data class Failure(val error: Throwable) : State()
    }
}