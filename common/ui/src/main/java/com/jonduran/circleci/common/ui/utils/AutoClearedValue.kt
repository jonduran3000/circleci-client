package com.jonduran.circleci.common.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class AutoClearedValue<T : Any>(fragment: Fragment) : ReadWriteProperty<Fragment, T> {
    init {
        val clearOnDestroy = object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _value = null
            }
        }

        val onCreate = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(clearOnDestroy)
                }
            }
        }

        fragment.lifecycle.addObserver(onCreate)
    }

    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException("Cannot access auto-cleared value.")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

internal fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)