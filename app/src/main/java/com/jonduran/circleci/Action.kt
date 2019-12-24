package com.jonduran.circleci

sealed class Action {
    object InitialLoad : Action()
}