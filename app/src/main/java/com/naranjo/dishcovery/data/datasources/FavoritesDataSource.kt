package com.naranjo.dishcovery.data.datasources

import com.naranjo.dishcovery.domain.entities.Recipe

interface FavoritesDataSource {
    suspend fun addFavorite(recipe: Recipe)

    suspend fun removeFavorite(recipe: Recipe)

    suspend fun getFavorites(): List<Recipe>
}