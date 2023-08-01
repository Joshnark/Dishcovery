package com.naranjo.dishcovery.domain.entities

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)