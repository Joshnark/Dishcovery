package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class RecipesRepositoryImpl(
    private val recipesDataSource: RecipesDataSource
): RecipesRepository {

    override suspend fun getRecipes(): List<Recipe> =
        recipesDataSource.getRecipes()

    override suspend fun getPopularRecipes(): List<Recipe>  =
        recipesDataSource.getPopularRecipes()

    override suspend fun getRecipesByCategory(category: String): List<Recipe> =
        recipesDataSource.getRecipesByCategory(category)

    override suspend fun getRecipeById(id: Int) =
        recipesDataSource.getRecipeById(id)

}