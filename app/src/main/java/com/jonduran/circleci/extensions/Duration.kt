package com.jonduran.circleci.extensions

import org.threeten.bp.Duration

fun Duration.prettyPrint(): String {
    val builder = StringBuilder()
    val hours = toHours()
    if (hours > 0) {
        builder.append(hours)
            .append("h")
            .append(" ")
    }
    val minutes = toMinutes()
    if (minutes > 0) {
        builder.append(minutes)
            .append("m")
            .append(" ")
    }
    if (seconds > 0) {
        builder.append(minutes).append("s")
    }
    return builder.toString()
}