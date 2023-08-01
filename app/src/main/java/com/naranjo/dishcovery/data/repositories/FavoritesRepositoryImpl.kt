package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class FavoritesRepositoryImpl(
    private val favoritesDataSource: FavoritesDataSource
): FavoritesRepository {

    override suspend fun addFavorite(recipe: Recipe) =
        favoritesDataSource.addFavorite(recipe)

    override suspend fun removeFavorite(recipe: Recipe) =
        favoritesDataSource.removeFavorite(recipe)

    override suspend fun getFavorites(): List<Recipe> =
        favoritesDataSource.getFavorites()

}