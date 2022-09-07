package com.pet.picker.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String = "N/A",
    val likes: Int = 0,
    val username: String = "N/A",
    val linkFull: String = "N/A",
    val linkRegular: String = "N/A",
    val linkThumb: String = "N/A",
) : Parcelable