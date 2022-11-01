package com.pet.picker.model

import com.pet.picker.model.entities.UnsplashPhoto

sealed class AppState {
    data class Success(val searchResults: List<UnsplashPhoto>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}