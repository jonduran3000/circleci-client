package com.jonduran.circleci

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jonduran.circleci.data.user.UserRepository
import com.jonduran.circleci.viewmodel.AssistedProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MainViewModel @AssistedInject constructor(
    @Assisted private val savedState: SavedStateHandle,
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

    @AssistedInject.Factory
    interface Provider : AssistedProvider<MainViewModel> {
        override fun provide(savedState: SavedStateHandle): MainViewModel
    }
}