package com.pet.picker.ui

import androidx.lifecycle.ViewModel
import com.pet.picker.model.AppState
import com.pet.picker.model.repository.Repository
import com.pet.picker.model.repository.RepositoryImpl
import com.pet.picker.plusAssign
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
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
        getSearchResultsFor()
    }

    fun onQueryChanged(query: String) {
        querySubject.onNext(query)
    }

    private fun getSearchResultsFor() {

        disposables += querySubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { query -> Observable.just(query) }
            .subscribe { query ->
                repository.getPhotos(query)
                    .doOnSubscribe { appStateSubject.onNext(AppState.Loading) }
                    .subscribe(
                        { photos -> appStateSubject.onNext(AppState.Success(photos)) },
                        { error: Throwable -> appStateSubject.onNext(AppState.Error(error)) }
                    )
            }
    }

    override fun onCleared() {
        disposables.clear()
    }
}
