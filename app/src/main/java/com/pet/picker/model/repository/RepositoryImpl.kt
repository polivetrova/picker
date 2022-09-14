package com.pet.picker.model.repository

import com.pet.picker.model.entities.PhotoDTO
import com.pet.picker.model.entities.UnsplashPhoto
import com.pet.picker.unsplashAPI.SearchRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryImpl : Repository {

    override fun getSearchResultsFor(query: String): List<UnsplashPhoto> {

        val data = mutableListOf<UnsplashPhoto>()
        val dto: Flowable<PhotoDTO> = SearchRepo.api.getSearchResult(query)
        dto.onBackpressureLatest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { photoList ->
                photoList.results.forEach { photo ->
                    data.add(
                        UnsplashPhoto(
                            id = photo.id ?: "N/A",
                            likes = photo.likes ?: 0,
                            username = photo.user.username ?: "N/A",
                            linkFull = photo.urls.full ?: "N/A",
                            linkRegular = photo.urls.regular ?: "N/A",
                            linkThumb = photo.urls.thumb ?: "N/A"
                        )
                    )
                }
            }
            .doOnError { e -> e.printStackTrace() }
            .subscribe()
        return data
    }
}