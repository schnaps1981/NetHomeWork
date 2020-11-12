package com.example.nethomework.network

import com.example.nethomework.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class Network {
    val imgurApi: ImgurAPI
        get() {
            val retrofitBuilder = Retrofit.Builder()

                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(ImgurAPI.BASE_URL)
                .client(client)
                .build()

            return retrofitBuilder.create(ImgurAPI::class.java)
        }

    private val client: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.BASIC

            val okHttp = OkHttpClient.Builder()
            okHttp.addInterceptor(logging)
            okHttp.protocols(Collections.singletonList(Protocol.HTTP_1_1))

            okHttp.connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)

            return okHttp.build()
        }

    companion object {
        const val CONNECTION_TIMEOUT_SECONDS = 30L
    }
}
