package com.jonduran.circleci.common.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<B : ViewBinding>(
    fragment: Fragment,
    private val factory: (View) -> B
) : AutoClearedValue<B>(fragment) {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        val value = _value

        if (value != null) {
            return value
        }

        with(thisRef.viewLifecycleOwner.lifecycle.currentState) {
            check(isAtLeast(Lifecycle.State.INITIALIZED))
        }

        return factory(thisRef.requireView()).also { _value = it }
    }
}

fun <B : ViewBinding> Fragment.viewBinding(factory: (View) -> B): FragmentViewBindingDelegate<B> {
    return FragmentViewBindingDelegate(this, factory)
}