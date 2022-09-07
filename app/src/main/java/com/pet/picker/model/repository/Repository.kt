package com.pet.picker.model.repository

import com.pet.picker.model.entities.UnsplashPhoto

interface Repository {

    fun getSearchResultsFor(query: String): List<UnsplashPhoto>
}