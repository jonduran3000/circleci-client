@file:Suppress("NOTHING_TO_INLINE")
package com.jonduran.circleci.data.utils

import android.os.Looper

inline fun checkMainThread() {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        throw IllegalStateException("Expected to be called on the main thread but was ${Thread.currentThread().name}")
    }
}

inline fun checkBackgroundThread() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        throw IllegalStateException("Expected to be called on background thread but was the main thread")
    }
}