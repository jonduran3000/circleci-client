package com.jonduran.circleci.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface AssistedProvider<V : ViewModel> {
    fun provide(savedState: SavedStateHandle): V
}