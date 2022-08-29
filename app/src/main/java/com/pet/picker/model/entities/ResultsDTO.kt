package com.pet.picker.model.entities

data class ResultsDTO(
    val likes: Int?,
    val user: UserDTO,
    val urls: LinksDTO
)
