package com.jonduran.circleci.common.ui.utils

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

inline fun <B : ViewBinding> Activity.viewBinding(
    crossinline inflateBinding: (LayoutInflater) -> B
) = lazy(LazyThreadSafetyMode.NONE) {
    inflateBinding(layoutInflater)
}