package com.pet.picker.unsplashAPI

import com.pet.picker.model.entities.PhotoDTO
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("search/photos")
    fun getSearchResult(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 20
    ): Flowable<PhotoDTO>
}