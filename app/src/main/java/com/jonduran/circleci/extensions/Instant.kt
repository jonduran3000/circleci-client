package com.jonduran.circleci.extensions

import android.content.Context
import android.text.format.DateUtils
import org.threeten.bp.Instant

fun Instant.elapsedTime(context: Context): CharSequence {
    return DateUtils.getRelativeTimeSpanString(context, toEpochMilli(), false)
}