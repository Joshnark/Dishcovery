package com.naranjo.dishcovery.data.datasources

import com.naranjo.dishcovery.domain.entities.Recipe

interface RecipesDataSource {
    suspend fun getRecipes(): List<Recipe>

    suspend fun getPopularRecipes(): List<Recipe>

    suspend fun getRecipesByCategory(category: String): List<Recipe>

    suspend fun searchRecipes(keyword: String): List<Recipe>

    suspend fun getRecipeById(id: Int): Recipe
}