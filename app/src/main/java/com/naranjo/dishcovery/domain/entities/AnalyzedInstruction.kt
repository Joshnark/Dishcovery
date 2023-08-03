package com.naranjo.dishcovery.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
): Parcelable