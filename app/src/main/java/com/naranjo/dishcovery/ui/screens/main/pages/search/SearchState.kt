package com.naranjo.dishcovery.ui.screens.main.pages.search

import com.naranjo.dishcovery.domain.entities.Recipe
import java.lang.Exception

sealed class SearchState {
    data object Idle: SearchState()
    data object Loading: SearchState()
    data class LoadRecipes(val recipes: List<Recipe>): SearchState()
    data class Error(val exception: Throwable): SearchState()
}