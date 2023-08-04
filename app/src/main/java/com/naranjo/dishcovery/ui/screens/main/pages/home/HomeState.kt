package com.naranjo.dishcovery.ui.screens.main.pages.home

import com.naranjo.dishcovery.domain.entities.Recipe
import java.lang.Exception

sealed class HomeState {
    data object Idle: HomeState()
    data object Loading: HomeState()
    data class LoadRecipes(val recipes: List<Recipe>): HomeState()
    data class Error(val exception: Throwable): HomeState()
}