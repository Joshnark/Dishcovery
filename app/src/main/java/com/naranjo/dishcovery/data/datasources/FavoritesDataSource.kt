package com.naranjo.dishcovery.data.datasources

import com.naranjo.dishcovery.domain.models.Recipe

interface FavoritesDataSource {
    suspend fun addFavorite(recipe: Recipe)

    suspend fun getFavorites(): List<Recipe>
}