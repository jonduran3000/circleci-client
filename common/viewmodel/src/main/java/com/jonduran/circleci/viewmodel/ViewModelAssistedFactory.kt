package com.jonduran.circleci.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<V : ViewModel> {
    fun create(savedState: SavedStateHandle): V
}