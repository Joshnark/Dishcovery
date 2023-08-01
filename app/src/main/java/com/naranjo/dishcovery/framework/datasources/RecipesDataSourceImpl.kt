package com.naranjo.dishcovery.framework.datasources

import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.framework.network.RecipesApi

class RecipesDataSourceImpl(
    private val recipesApi: RecipesApi
): RecipesDataSource {

    override suspend fun getRecipes(): List<Recipe> {
        return recipesApi.getRecipes().data
    }

    override suspend fun getPopularRecipes(): List<Recipe> {
        return recipesApi.getRecipes(
            isPopular = true
        ).data
    }

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        return recipesApi.getRecipes(
            category = category
        ).data
    }

    override suspend fun getRecipeById(id: Int): Recipe {
        return recipesApi.getRecipeById(
            id = id
        ).data
    }

}