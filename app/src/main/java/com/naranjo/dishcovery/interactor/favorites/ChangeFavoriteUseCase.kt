package com.naranjo.dishcovery.interactor.favorites

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository

class ChangeFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(recipe: Recipe): Result<Boolean> {
        return favoritesRepository.changeFavorite(recipe)
    }

}