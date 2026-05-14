package com.karunada.karunaduuvanya.data.model

data class Wildlife(
    val id: String,
    val name: String,
    val scientificName: String,
    val description: String,
    val habitat: String,
    val status: String,
    val imageUrl: String,
    val fact: String,
    val detailImages: List<String> = emptyList()
)
