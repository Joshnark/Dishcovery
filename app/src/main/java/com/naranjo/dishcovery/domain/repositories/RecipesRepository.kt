package com.naranjo.dishcovery.domain.repositories

import com.naranjo.dishcovery.domain.models.Recipe

interface RecipesRepository {

    suspend fun getRecipes(): List<Recipe>

    suspend fun getPopularRecipes(): List<Recipe>

    suspend fun getRecipesByCategory(): List<Recipe>

    suspend fun getRecipeById(id: Int): Recipe

}