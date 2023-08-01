package com.naranjo.dishcovery.interactor.favorites

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class AddFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        favoritesRepository.addFavorite(recipe)
    }
}