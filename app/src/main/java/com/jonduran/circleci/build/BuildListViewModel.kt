package com.jonduran.circleci.build

import android.app.Application
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.cache.entity.BuildEntity
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.data.build.BuildRepository
import com.jonduran.circleci.extensions.elapsedTime
import com.jonduran.circleci.extensions.prettyPrint
import com.jonduran.circleci.extensions.safeOffer
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.temporal.ChronoUnit.MILLIS
import javax.inject.Inject

class BuildListViewModel(
    application: Application,
    savedState: SavedStateHandle,
    private val repository: BuildRepository
) : AndroidViewModel(application) {
    private val _state = ConflatedBroadcastChannel<State>()

    fun state(): Flow<State> = _state.asFlow()

    fun getRecentBuilds() {
        viewModelScope.launch {
            _state.safeOffer(State.Loading)
            try {
                val builds = repository.getRecentBuilds().map { build ->
                    BuildItem(
                        buildUrl = build.buildUrl,
                        avatarUrl = build.avatarUrl,
                        subject = build.subject,
                        username = build.username,
                        reponame = build.reponame,
                        gitSha = build.gitSha.substring(0, 7),
                        duration = build.buildTime.prettyPrint(),
                        branch = build.branch,
                        startTime = build.startTime?.elapsedTime(getApplication())
                    )
                }
                _state.safeOffer(State.Success(builds))
            } catch (e: Exception) {
                _state.safeOffer(State.Failure(e))
            }
        }
    }

    private val BuildEntity.buildTime: Duration get() = Duration.of(buildTimeInMillis, MILLIS)

    sealed class State {
        object Loading : State()
        data class Success(val builds: List<Item>) : State()
        data class Failure(val error: Throwable) : State()
    }

    class Factory @Inject constructor(
        fragment: BuildListFragment,
        private val application: Application,
        private val repository: BuildRepository
    ) : AbstractSavedStateViewModelFactory(fragment, fragment.arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ) = BuildListViewModel(application, handle, repository) as T
    }
}