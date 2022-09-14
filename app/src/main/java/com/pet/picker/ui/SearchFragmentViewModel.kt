package com.pet.picker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pet.picker.model.AppState
import com.pet.picker.model.entities.UnsplashPhoto
import com.pet.picker.model.repository.Repository
import com.pet.picker.model.repository.RepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragmentViewModel : ViewModel() {

    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val searchResultsLiveData: LiveData<AppState> get() = localLiveData
    private val repository: Repository = RepositoryImpl()

    fun getSearchResultsFor(query: String) {

        localLiveData.value = AppState.Loading

        var data = listOf<UnsplashPhoto>()

        Flowable.just(BackpressureStrategy.LATEST)
            .doOnNext { data = repository.getSearchResultsFor(query) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { localLiveData.postValue(AppState.Success(data)) }
            .subscribe()
    }
}