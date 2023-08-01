package com.naranjo.dishcovery.domain.repositories

import com.naranjo.dishcovery.domain.entities.Recipe

interface FavoritesRepository {
    suspend fun addFavorite(recipe: Recipe)
    suspend fun removeFavorite(recipe: Recipe)
    suspend fun getFavorites(): List<Recipe>
}