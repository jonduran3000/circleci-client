package com.jonduran.circleci

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.data.Repository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _state = ConflatedBroadcastChannel<State>()
    val state: Flow<State> = _state.asFlow()

    fun process(action: Action) {
        viewModelScope.launch {
            _state.offer(State.Loading)
            try {
                if (repository.hasCredentials()) {
                    _state.offer(State.Success)
                } else {
                    _state.offer(State.Unauthorized)
                }
            } catch (e: Exception) {
                _state.offer(State.Failure(e))
            }
        }
    }

    object Factory : ViewModelProvider.Factory {
        private lateinit var repository: Repository

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }

        operator fun invoke(repository: Repository): Factory {
            if (!this::repository.isInitialized) {
                this.repository = repository
            }
            return this
        }
    }

    sealed class Action {
        object LoadData : Action()
    }

    sealed class State {
        object Loading : State()
        object Success : State()
        object Unauthorized : State()
        data class Failure(val error: Throwable) : State()
    }
}