package com.jonduran.circleci.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

fun ImageView.load(url: String, builder: RequestBuilder<Drawable>.() -> RequestBuilder<Drawable>) {
    Glide.with(this).load(url).builder().into(this)
}