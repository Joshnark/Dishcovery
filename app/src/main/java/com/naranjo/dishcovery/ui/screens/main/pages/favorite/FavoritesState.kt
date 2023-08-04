package com.naranjo.dishcovery.ui.screens.main.pages.favorite

import com.naranjo.dishcovery.domain.entities.Recipe
import java.lang.Exception

sealed class FavoritesState {
    data object Idle: FavoritesState()
    data object Loading: FavoritesState()
    data class LoadRecipes(val recipes: List<Recipe>): FavoritesState()
    data class Error(val exception: Throwable): FavoritesState()
}