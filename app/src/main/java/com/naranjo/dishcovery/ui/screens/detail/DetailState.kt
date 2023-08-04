package com.naranjo.dishcovery.ui.screens.detail

import com.naranjo.dishcovery.domain.entities.Recipe
import java.lang.Exception

sealed class DetailState {
    data object Idle: DetailState()
    data object Loading: DetailState()
    data class LoadRecipe(val recipe: Recipe): DetailState()
    data class Error(val exception: Throwable): DetailState()
}