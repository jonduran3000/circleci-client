@file:Suppress("NOTHING_TO_INLINE")
package com.jonduran.circleci.extensions

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.jonduran.circleci.common.android.ColorResource
import com.jonduran.circleci.common.android.DrawableResource

inline fun Context.getDrawableCompat(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

inline fun Context.getDrawableCompat(resource: DrawableResource): Drawable? {
    return ContextCompat.getDrawable(this, resource.res)
}

@ColorInt
inline fun Context.getColorCompat(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

@ColorInt
inline fun Context.getColorCompat(resource: ColorResource): Int {
    return ContextCompat.getColor(this, resource.res)
}

inline fun <reified A : Application> Context.getApplication(): A {
    return applicationContext as A
}