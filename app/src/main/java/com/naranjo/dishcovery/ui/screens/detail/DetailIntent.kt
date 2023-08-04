package com.naranjo.dishcovery.ui.screens.detail

import com.naranjo.dishcovery.domain.entities.Recipe

sealed class DetailIntent {
    data class GetRecipe(val id: Int): DetailIntent()
    data class ChangeFavoriteState(val recipe: Recipe): DetailIntent()
}