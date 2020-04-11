package com.jonduran.circleci.key

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.data.user.UserRepository
import com.jonduran.circleci.extensions.safeOffer
import com.jonduran.circleci.viewmodel.AssistedProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KeyEntryViewModel @AssistedInject constructor(
    @Assisted savedState: SavedStateHandle,
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

    @AssistedInject.Factory
    interface Provider : AssistedProvider<KeyEntryViewModel> {
        override fun provide(savedState: SavedStateHandle): KeyEntryViewModel
    }
}