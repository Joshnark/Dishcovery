package com.naranjo.dishcovery.framework.datasources

import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.framework.network.RecipesApi

class RecipesDataSourceImpl(
    private val recipesApi: RecipesApi
): RecipesDataSource {

    override suspend fun getRecipes(): List<Recipe> {
        return recipesApi.getRecipes().data.distinctBy { it.id }
    }

    override suspend fun getPopularRecipes(): List<Recipe> {
        return recipesApi.getRecipes(
            isPopular = true
        ).data.distinctBy { it.id }
    }

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        return recipesApi.getRecipes(
            category = category
        ).data.distinctBy { it.id }
    }

    override suspend fun searchRecipes(keyword: String): List<Recipe> {
        return recipesApi.getRecipes(
            keyword = keyword
        ).data.distinctBy { it.id }
    }

    override suspend fun getRecipeById(id: Int): Recipe {
        return recipesApi.getRecipeById(
            id = id
        ).data
    }

}