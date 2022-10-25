package com.pet.picker.model.repository

import com.pet.picker.model.entities.UnsplashPhoto
import com.pet.picker.unsplashAPI.SearchRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryImpl : Repository {

    override fun getPhotos(query: String): Single<List<UnsplashPhoto>> {

        return SearchRepo.api.getSearchResult(query)
            .subscribeOn(Schedulers.io())
            .map { photoList ->
                photoList.results.map { photo ->
                    UnsplashPhoto(
                        id = photo.id,
                        likes = photo.likes,
                        username = photo.user.username,
                        linkFull = photo.urls.full,
                        linkRegular = photo.urls.regular,
                        linkThumb = photo.urls.thumb
                    )
                }
            }
    }
}