package com.jonduran.circleci.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.coroutines.CoroutineScope

fun Fragment.launchWhenViewCreated(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwnerLiveData.observe(this) { owner ->
        owner.lifecycleScope.launchWhenCreated(block)
    }
}