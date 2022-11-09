package com.pet.picker.ui

import androidx.lifecycle.ViewModel
import com.pet.picker.model.AppState
import com.pet.picker.model.repository.Repository
import com.pet.picker.model.repository.RepositoryImpl
import com.pet.picker.plusAssign
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchFragmentViewModel : ViewModel() {

    private val repository: Repository = RepositoryImpl()
    private val disposables: CompositeDisposable = CompositeDisposable()

    private val appStateSubject: BehaviorSubject<AppState> = BehaviorSubject.create()
    val appStateFlowable: Flowable<AppState> =
        appStateSubject.toFlowable(BackpressureStrategy.LATEST)
    private val querySubject: PublishSubject<String> = PublishSubject.create()

    init {
        getSearchResults()
    }

    fun onQueryChanged(query: String) {
        querySubject.onNext(query)
    }

    private fun getSearchResults() {

        disposables += querySubject
            .subscribeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { query ->
                repository.getPhotos(query)
                    .doOnSubscribe { appStateSubject.onNext(AppState.Loading) }
                    .doOnError { error: Throwable -> appStateSubject.onNext(AppState.Error(error)) }
                    .doOnSuccess { photos -> appStateSubject.onNext(AppState.Success(photos)) }
                    .toObservable()
            }
            .subscribe()
    }

    override fun onCleared() {
        disposables.clear()
    }
}
