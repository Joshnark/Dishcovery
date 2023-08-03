package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class RecipesRepositoryImpl(
    private val recipesDataSource: RecipesDataSource,
    private val favoritesDataSource: FavoritesDataSource
): RecipesRepository {

    override suspend fun getRecipes(): Result<List<Recipe>> {
        return runCatching {
            recipesDataSource.getRecipes().checkFavorites()
        }
    }

    override suspend fun getPopularRecipes(): Result<List<Recipe>> {
        return runCatching {
            recipesDataSource.getPopularRecipes().checkFavorites()
        }
    }

    override suspend fun getRecipesByCategory(category: String): Result<List<Recipe>> {
        return runCatching {
            recipesDataSource.getRecipesByCategory(category).checkFavorites()
        }
    }

    override suspend fun searchRecipes(keyword: String): Result<List<Recipe>> {
        return runCatching {
            recipesDataSource.searchRecipes(keyword).checkFavorites()
        }
    }

    override suspend fun getRecipeById(id: Int): Result<Recipe> {
        return runCatching {
            recipesDataSource.getRecipeById(id)
        }
    }

    private suspend fun List<Recipe>.checkFavorites(): List<Recipe> {
        val favorites = favoritesDataSource.getFavorites()

        return this.map { recipe ->
            if (favorites.any { it.id == recipe.id }) {
                recipe.copy(isFavorite = true)
            } else {
                recipe.copy(isFavorite = false)
            }
        }
    }

}