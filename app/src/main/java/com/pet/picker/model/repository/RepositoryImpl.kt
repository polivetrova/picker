package com.pet.picker.model.repository

import com.pet.picker.model.entities.PhotoDTO
import com.pet.picker.model.entities.UnsplashPhoto
import com.pet.picker.unsplashAPI.SearchRepo

class RepositoryImpl : Repository {

    override fun getSearchResultsFor(query: String): List<UnsplashPhoto> {

        val dto: PhotoDTO? = SearchRepo.api.getSearchResult(query).execute().body()
        val data = mutableListOf<UnsplashPhoto>()

        dto?.results?.forEach {
            data.add(
                UnsplashPhoto(
                    id = it.id ?: "N/A",
                    likes = it.likes ?: 0,
                    username = it.user.username ?: "N/A",
                    linkFull = it.urls.full ?: "N/A",
                    linkRegular = it.urls.regular ?: "N/A",
                    linkThumb = it.urls.thumb ?: "N/A"
                )
            )
        }
        return data
    }
}