package com.naranjo.dishcovery.ui.screens.main.pages.home

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.screens.main.models.Category

sealed class HomeIntent {
    data class GetRecipes(val selectedCategory: Category? = null, val load: Boolean = true): HomeIntent()
    data class ChangeFavorite(val recipe: Recipe): HomeIntent()
}