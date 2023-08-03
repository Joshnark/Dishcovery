package com.naranjo.dishcovery.domain.repositories

import com.naranjo.dishcovery.domain.entities.Recipe

interface RecipesRepository {

    suspend fun getRecipes(): Result<List<Recipe>>

    suspend fun getPopularRecipes(): Result<List<Recipe>>

    suspend fun getRecipesByCategory(category: String): Result<List<Recipe>>

    suspend fun searchRecipes(keyword: String): Result<List<Recipe>>

    suspend fun getRecipeById(id: Int): Result<Recipe>


}