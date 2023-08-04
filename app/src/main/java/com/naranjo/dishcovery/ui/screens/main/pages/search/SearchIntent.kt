package com.naranjo.dishcovery.ui.screens.main.pages.search

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.screens.main.models.Category

sealed class SearchIntent {
    data class SearchRecipes(val keyword: String? = null, val load: Boolean = true): SearchIntent()
    data class ChangeFavorite(val recipe: Recipe): SearchIntent()
}