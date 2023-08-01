package com.naranjo.dishcovery.domain.entities

data class Ingredient(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
)