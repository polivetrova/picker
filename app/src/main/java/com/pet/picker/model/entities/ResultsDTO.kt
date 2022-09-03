package com.pet.picker.model.entities

data class ResultsDTO(
    val likes: Int?,
    val id: String?,
    val user: UserDTO,
    val urls: LinksDTO
)
