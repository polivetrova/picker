package com.pet.picker.unsplashAPI

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object APIUtils {

    val baseURL = "https://api.unsplash.com/"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Client-ID 2hrUuwPdFfT2ulv4lfoyolx-7mBLsY2FCLeOQuCV-u8")
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }
        return httpClient.build()
    }
}