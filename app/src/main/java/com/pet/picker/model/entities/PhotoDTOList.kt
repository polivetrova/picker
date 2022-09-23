package com.pet.picker.model.entities

data class PhotoDTOList(
    val results: List<ResultsDTO>
) {
    data class ResultsDTO(
        val likes: Int?,
        val id: String?,
        val user: UserDTO,
        val urls: LinksDTO
    ) {
        data class UserDTO(
            val username: String?
        )

        data class LinksDTO(
            val full: String?,
            val regular: String?,
            val thumb: String?
        )
    }
}
