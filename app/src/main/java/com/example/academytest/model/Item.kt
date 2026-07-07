package com.example.academytest.model

import java.util.UUID

data class Item(
    val id: UUID = UUID.randomUUID(),
    val creationIndex: Int,
    val name: String,
    val isFavorite: Boolean = false
)