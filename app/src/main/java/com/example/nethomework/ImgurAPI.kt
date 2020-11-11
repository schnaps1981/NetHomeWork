package com.example.nethomework

import okhttp3.MultipartBody
import retrofit2.http.*
import kotlin.random.Random


interface ImgurAPI {

    @Multipart
    @Headers(value = ["Authorization: Bearer $TOKEN"])
    @POST("3/upload")
    suspend fun uploadPhoto(
        @Header("Authorization") auth: String = "Client-ID $CLIENT_ID",
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("type") type: String = "file",
        @Query("name") name: String = "file_${Random.nextInt(1000, 9999)}",
        @Part  file: MultipartBody.Part
    ): ImageResponse

    companion object {
        const val BASE_URL = "https://api.imgur.com/"
        const val CLIENT_ID = "546c25a59c58ad7"
        const val TOKEN = "cd75ee6c74ecba7bf92fcdf77c53ebba6b27bdf8"
    }
}