package com.naranjo.dishcovery.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Length(
    val number: Int,
    val unit: String
): Parcelable