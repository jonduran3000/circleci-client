package com.jonduran.circleci

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jonduran.circleci.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainViewModel(
    private val savedState: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {

    val state: Flow<State> = flow {
        if (savedState.contains("savedInstance")) {
            emit(State.SavedInstance)
        } else {
            emit(State.Loading)
            savedState.set("savedInstance", Bundle())
            if (repository.hasCredentials()) {
                emit(State.Success)
            } else {
                emit(State.Unauthorized)
            }
        }
    }.catch { e ->
        emit(State.Failure(e))
    }

    sealed class State {
        object Loading : State()
        object SavedInstance : State()
        object Success : State()
        object Unauthorized : State()
        data class Failure(val error: Throwable) : State()
    }

    class Factory @Inject constructor(
        activity: MainActivity,
        private val repository: UserRepository
    ) : AbstractSavedStateViewModelFactory(activity, activity.intent.extras) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return MainViewModel(handle, repository) as T
        }
    }
}