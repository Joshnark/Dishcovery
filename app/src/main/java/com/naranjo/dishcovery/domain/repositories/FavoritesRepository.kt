package com.naranjo.dishcovery.domain.repositories

import com.naranjo.dishcovery.domain.models.Recipe

interface FavoritesRepository {
    suspend fun addFavorite(recipe: Recipe)
    suspend fun getFavorites(): List<Recipe>
}