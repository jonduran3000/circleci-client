package com.jonduran.circleci.extensions

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMarginsRelative
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.snackbar.Snackbar
import com.jonduran.circleci.R

fun Snackbar.float() {
    // Update the background
    val model = ShapeAppearanceModel
        .builder(context, R.style.ShapeAppearance_Stable_Snackbar, 0)
        .build()
    val background = MaterialShapeDrawable(model)
    view.background = background

    // Update the margins
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        val margin = view.resources.getDimensionPixelSize(R.dimen.snackbar_margin)
        updateMarginsRelative(margin, margin, margin, margin)
    }

    // Update the elevation
    val elevation = view.resources.getDimension(R.dimen.snackbar_elevation)
    ViewCompat.setElevation(view, elevation)
    show()
}