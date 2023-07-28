package com.naranjo.dishcovery.domain.models

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)