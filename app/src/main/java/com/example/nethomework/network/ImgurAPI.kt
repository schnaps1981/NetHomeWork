package com.example.nethomework.network

import com.example.nethomework.network.models.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import kotlin.random.Random

interface ImgurAPI {

    @Multipart
    @Headers(value = ["Authorization: Bearer $TOKEN"])
    @POST("3/upload")
    suspend fun uploadPhoto(
        @Part title: MultipartBody.Part = MultipartBody.Part.createFormData("title", ""),

        @Part description: MultipartBody.Part = MultipartBody.Part.createFormData(
            "description",
            ""
        ),

        @Part type: MultipartBody.Part = MultipartBody.Part.createFormData("type", "file"),

        @Part name: MultipartBody.Part = MultipartBody.Part.createFormData(
            "name",
            "file_${Random.nextInt(1000, 9999)}"
        ),

        @Part image: MultipartBody.Part
    ): ImageResponse

    companion object {
        const val BASE_URL = "https://api.imgur.com/"
        const val TOKEN = "cd75ee6c74ecba7bf92fcdf77c53ebba6b27bdf8"
    }
}
