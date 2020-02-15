package com.jonduran.circleci.extensions

import kotlinx.coroutines.channels.SendChannel

fun <E> SendChannel<E>.safeOffer(value: E) = !isClosedForSend && try {
    offer(value)
} catch (ignored: Throwable) {
    false
}