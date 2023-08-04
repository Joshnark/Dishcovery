package com.naranjo.dishcovery.domain.repositories

import com.naranjo.dishcovery.domain.entities.Recipe

interface FavoritesRepository {
    suspend fun changeFavorite(recipe: Recipe): Result<Boolean>
    suspend fun getFavorites(): Result<List<Recipe>>
}