package com.naranjo.dishcovery.ui.screens.main.pages.home

import com.naranjo.dishcovery.ui.screens.main.models.Category

sealed class HomeIntent {
    data class GetRecipes(val selectedCategory: Category): HomeIntent()
}