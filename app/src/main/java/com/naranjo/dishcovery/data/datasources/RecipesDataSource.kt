package com.naranjo.dishcovery.data.datasources

import com.naranjo.dishcovery.domain.models.Recipe

interface RecipesDataSource {
    suspend fun getRecipes(): List<Recipe>

    suspend fun getPopularRecipes(): List<Recipe>

    suspend fun getRecipesByCategory(): List<Recipe>

    suspend fun getRecipeById(id: Int): Recipe
}