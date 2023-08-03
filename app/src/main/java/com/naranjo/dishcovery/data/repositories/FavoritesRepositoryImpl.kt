package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class FavoritesRepositoryImpl(
    private val favoritesDataSource: FavoritesDataSource
): FavoritesRepository {

    override suspend fun changeFavorite(recipe: Recipe): Result<Boolean> {
        return runCatching {
            val isRecipeAddedToFavorites = favoritesDataSource.getRecipe(recipe).isNotEmpty()

            if (isRecipeAddedToFavorites) {
                favoritesDataSource.removeFavorite(recipe)
                false
            } else {
                favoritesDataSource.addFavorite(recipe)
                true
            }
        }
    }

    override suspend fun getFavorites(): Result<List<Recipe>> {
        return runCatching {
            favoritesDataSource.getFavorites().map { it.copy(isFavorite = true) }
        }
    }

}