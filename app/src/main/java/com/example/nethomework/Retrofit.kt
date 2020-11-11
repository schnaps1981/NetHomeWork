package com.example.nethomework

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Network {
    val imgurApi: ImgurAPI
    get() {
        val retrofitBuilder = Retrofit.Builder()

            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(ImgurAPI.BASE_URL)
            .build()

        return retrofitBuilder.create(ImgurAPI::class.java)
    }
}