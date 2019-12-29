package com.jonduran.circleci.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jonduran.circleci.R

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.image_corner_radius)))
        .into(this)
}