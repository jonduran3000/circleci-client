package com.jonduran.circleci

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Failure<T>(val error: Throwable) : State<T>()

    companion object {
        @JvmStatic fun <T> loading(): State<T> = Loading()
        @JvmStatic fun <T> success(data: T): State<T> = Success(data)
        @JvmStatic fun <T> failure(error: Throwable): State<T> = Failure(error)
    }
}