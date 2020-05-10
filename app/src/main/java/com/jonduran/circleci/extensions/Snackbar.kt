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
    updateBackground()
    updateMargins()
    elevate()
    show()
}

private fun Snackbar.updateBackground() {
    val model = ShapeAppearanceModel
        .builder(context, R.style.ShapeAppearance_Stable_Snackbar, 0)
        .build()
    view.background = MaterialShapeDrawable(model)
}

private fun Snackbar.updateMargins() {
    val margin = view.resources.getDimensionPixelSize(R.dimen.snackbar_margin)
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        updateMarginsRelative(margin, margin, margin, margin)
    }
}

private fun Snackbar.elevate() {
    val elevation = view.resources.getDimension(R.dimen.snackbar_elevation)
    ViewCompat.setElevation(view, elevation)
}