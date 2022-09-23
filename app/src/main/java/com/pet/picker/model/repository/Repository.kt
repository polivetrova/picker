package com.pet.picker.model.repository

import com.pet.picker.model.entities.UnsplashPhoto
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun getResultsFor(query: String): Single<List<UnsplashPhoto>>
}