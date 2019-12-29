package com.jonduran.circleci.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <X, Y, Z> LiveData<X>.combineLatest(other: LiveData<Y>, onChange: (X, Y) -> Z): LiveData<Z> {
    var thisSourceEmitted = false
    var otherSourceEmitted = false

    val result = MediatorLiveData<Z>()

    val mergeFunction = {
        val thisValue = this.value
        val otherValue = other.value

        if (thisSourceEmitted && otherSourceEmitted) {
            result.value = onChange.invoke(thisValue!!, otherValue!!)
        }
    }

    result.addSource(this) {
        thisSourceEmitted = true
        mergeFunction.invoke()
    }

    result.addSource(other) {
        otherSourceEmitted = true
        mergeFunction.invoke()
    }

    return result
}