package com.jonduran.circleci

import androidx.viewbinding.ViewBinding

abstract class UiComponent<V : ViewBinding, in S>(protected val binding: V) {
    abstract fun render(state: S)
}