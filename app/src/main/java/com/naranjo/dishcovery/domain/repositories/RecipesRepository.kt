package com.naranjo.dishcovery.domain.repositories

import com.naranjo.dishcovery.domain.entities.Recipe

interface RecipesRepository {

    suspend fun getRecipes(): List<Recipe>

    suspend fun getPopularRecipes(): List<Recipe>

    suspend fun getRecipesByCategory(category: String): List<Recipe>

    suspend fun getRecipeById(id: Int): Recipe

}