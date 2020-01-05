package com.jonduran.circleci.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T> Flow<T>.observe(scope: CoroutineScope, crossinline onChange: (T) -> Unit) {
    this.onEach { onChange(it) }.launchIn(scope)
}