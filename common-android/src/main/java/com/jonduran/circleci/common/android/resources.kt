package com.jonduran.circleci.common.android

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

inline class ColorResource(@ColorRes val res: Int)
inline class DrawableResource(@DrawableRes val res: Int)
inline class IdResource(@IdRes val res: Int)
inline class LayoutResource(@LayoutRes val res: Int)
