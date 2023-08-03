package com.naranjo.dishcovery.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
): Parcelable