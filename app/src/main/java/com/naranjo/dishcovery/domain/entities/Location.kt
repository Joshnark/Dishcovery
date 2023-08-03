package com.naranjo.dishcovery.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double,
): Parcelable
