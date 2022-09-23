package com.pet.picker.model.repository

import com.pet.picker.model.entities.PhotoDTO
import com.pet.picker.model.entities.UnsplashPhoto
import com.pet.picker.unsplashAPI.SearchRepo
import io.reactivex.rxjava3.core.Single

class RepositoryImpl : Repository {

    override fun getResultsFor(query: String): Single<List<UnsplashPhoto>> {

        val dto: Single<PhotoDTO> = SearchRepo.api.getSearchResult(query)
        return dto.map { photoList ->
            photoList.results.map { photo ->
                UnsplashPhoto(
                    id = photo.id ?: "N/A",
                    likes = photo.likes ?: 0,
                    username = photo.user.username ?: "N/A",
                    linkFull = photo.urls.full ?: "N/A",
                    linkRegular = photo.urls.regular ?: "N/A",
                    linkThumb = photo.urls.thumb ?: "N/A"
                )
            }
        }
    }
}