package com.example.nethomework.network

data class Result<T>(val data: T, val error: Throwable? = null) {

    fun hasError() = error != null
}
