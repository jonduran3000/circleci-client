package com.jonduran.circleci

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.data.Repository

class BuildListViewModel(private val repository: Repository) : ViewModel() {

    val versionControl = MediatorLiveData<Array<SourceControl>>().apply {
        value = SourceControl.values()
    }

    val organization = MediatorLiveData<String>().apply {
        value = "%%"
    }

    val state = versionControl.combineLatest(organization) { vcs, org -> Pair(vcs, org) }
        .switchMap { (vcs, org) ->
            liveData {
                emit(State.loading())
                try {
                    val projects = repository.getListOfProjects(vcs, org)
                        .map { entity -> ProjectItem(entity.name, entity.username) }
                    emit(State.success(projects))
                } catch (e: Exception) {
                    emit(State.failure(e))
                }
            }
        }
}