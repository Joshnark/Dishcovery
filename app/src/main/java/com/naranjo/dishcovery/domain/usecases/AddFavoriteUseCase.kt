package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.models.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class AddFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        favoritesRepository.addFavorite(recipe)
    }
}