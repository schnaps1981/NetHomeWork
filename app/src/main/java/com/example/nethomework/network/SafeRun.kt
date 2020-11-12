package com.example.nethomework.network

import timber.log.Timber

inline fun <T> safeRun(
    block: () -> T,
    default: T
): Result<T> {
    return try {
        Result(block())
    } catch (e: Throwable) {
        Timber.e(e)
        Result(default, e)
    }
}
