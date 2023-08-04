package com.naranjo.dishcovery.ui.screens.main.pages.favorite

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.screens.main.models.Category

sealed class FavoritesIntent {
    data class GetFavorites(val load: Boolean): FavoritesIntent()
    data class ChangeFavorite(val recipe: Recipe): FavoritesIntent()
}