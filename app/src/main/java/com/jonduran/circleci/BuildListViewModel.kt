package com.jonduran.circleci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.data.Repository
import kotlinx.coroutines.launch

class BuildListViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MediatorLiveData<State<List<ProjectItem>>>()
    val state: LiveData<State<List<ProjectItem>>> = _state

    fun process(action: Action) {
        viewModelScope.launch {
            try {
                _state.value = State.loading()
                val projects = repository.getListOfProjects("Rightpoint", SourceControl.values())
                    .map { entity ->
                        ProjectItem(entity.name, entity.username)
                    }
                _state.value = State.success(projects)
            } catch (e: Exception) {
                _state.value = State.failure(e)
            }
        }
    }
}