package com.pet.picker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pet.picker.model.AppState
import com.pet.picker.model.repository.Repository
import com.pet.picker.model.repository.RepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class SearchFragmentViewModel : ViewModel() {

    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val searchResultsLiveData: LiveData<AppState> get() = localLiveData
    private val repository: Repository = RepositoryImpl()
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun getSearchResultsFor(query: String) {

        val disposable = repository.getResultsFor(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { localLiveData.value = AppState.Loading }
            .subscribe(
                { photos -> localLiveData.value = AppState.Success(photos) },
                { error: Throwable -> localLiveData.value = AppState.Error(error) }
            )
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
    }
}
