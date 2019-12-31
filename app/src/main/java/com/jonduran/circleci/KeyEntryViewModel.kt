package com.jonduran.circleci

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.data.Repository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class KeyEntryViewModel(private val repository: Repository) : ViewModel() {
    private val _state = ConflatedBroadcastChannel<State>()
    val state: Flow<State> = _state.asFlow()

    fun storeApiKey(key: CharSequence?) {
        viewModelScope.launch {
            if (key.isNullOrEmpty()) {
                _state.offer(State.EmptyKey)
            } else {
                try {
                    repository.storeKey(key.toString())
                    // TODO: add a verification check
                    _state.offer(State.Success)
                } catch (e: Exception) {
                    _state.offer(State.Failure(e))
                }
            }
        }
    }

    object Factory : ViewModelProvider.Factory {
        private lateinit var repository: Repository

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return KeyEntryViewModel(repository) as T
        }

        operator fun invoke(repository: Repository): Factory {
            if (!this::repository.isInitialized) {
                this.repository = repository
            }
            return this
        }
    }

    sealed class State {
        object EmptyKey : State()
        object Success : State()
        data class Failure(val error: Throwable) : State()
    }
}