package com.pet.picker.unsplashAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchRepo {
    val api: SearchAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(APIUtils.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(APIUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(SearchAPI::class.java)
    }
}