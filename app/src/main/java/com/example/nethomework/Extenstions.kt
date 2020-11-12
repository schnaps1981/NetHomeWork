package com.example.nethomework

import okhttp3.MultipartBody

fun String.toMultipart(name: String) =
    MultipartBody.Part.createFormData(name, this)
