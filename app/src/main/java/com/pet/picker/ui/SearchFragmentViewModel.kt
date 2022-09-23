package com.pet.picker.ui

import androidx.lifecycle.ViewModel
import com.pet.picker.model.AppState
import com.pet.picker.model.repository.Repository
import com.pet.picker.model.repository.RepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


class SearchFragmentViewModel : ViewModel() {

    private val repository: Repository = RepositoryImpl()
    private val disposables: CompositeDisposable = CompositeDisposable()

    private val localSubject: BehaviorSubject<AppState> = BehaviorSubject.create()
    val behaviorSubject: BehaviorSubject<AppState> get() = localSubject

    fun getSearchResultsFor(query: String) {

        val disposable = repository.getResultsFor(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { localSubject.onNext(AppState.Loading) }
            .subscribe(
                { photos -> localSubject.onNext(AppState.Success(photos)) },
                { error: Throwable -> localSubject.onNext(AppState.Error(error)) }
            )
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
    }
}
