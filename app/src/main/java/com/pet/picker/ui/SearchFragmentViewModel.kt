package com.pet.picker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pet.picker.model.AppState
import com.pet.picker.model.repository.Repository
import com.pet.picker.model.repository.RepositoryImpl

class SearchFragmentViewModel : ViewModel() {

    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val searchResultsLiveData: LiveData<AppState> get() = localLiveData
    private val repository: Repository = RepositoryImpl()

    fun getSearchResultsFor(query: String) {

        localLiveData.value = AppState.Loading
        val data = repository.getSearchResultsFor(query)
        localLiveData.value = AppState.Success(data)
    }
}
