package com.naranjo.dishcovery.interactor.favorites

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class GetFavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(): Result<List<Recipe>> {
        return favoritesRepository.getFavorites()
    }
}