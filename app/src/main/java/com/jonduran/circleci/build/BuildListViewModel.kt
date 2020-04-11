package com.jonduran.circleci.build

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.jonduran.circleci.CircleCiApp
import com.jonduran.circleci.cache.entity.BuildEntity
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.data.build.BuildRepository
import com.jonduran.circleci.extensions.elapsedTime
import com.jonduran.circleci.extensions.prettyPrint
import com.jonduran.circleci.viewmodel.AssistedProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.temporal.ChronoUnit.MILLIS

class BuildListViewModel @AssistedInject constructor(
    @Assisted savedState: SavedStateHandle,
    application: Application,
    private val repository: BuildRepository
) : AndroidViewModel(application) {

    val state: Flow<State> = flow {
        emit(State.Loading)
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
                    startTime = build.startTime?.elapsedTime(getApplication<CircleCiApp>())
                )
            }
            emit(State.Success(builds))
        } catch (e: Exception) {
            emit(State.Failure(e))
        }
    }

    private val BuildEntity.buildTime: Duration get() = Duration.of(buildTimeInMillis, MILLIS)

    @AssistedInject.Factory
    interface Provider : AssistedProvider<BuildListViewModel> {
        override fun provide(savedState: SavedStateHandle): BuildListViewModel
    }

    sealed class State {
        object Loading : State()
        data class Success(val builds: List<Item>) : State()
        data class Failure(val error: Throwable) : State()
    }
}