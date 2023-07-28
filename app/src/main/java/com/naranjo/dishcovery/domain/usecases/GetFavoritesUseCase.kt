package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.models.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class GetFavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(): List<Recipe> {
        return favoritesRepository.getFavorites()
    }
}