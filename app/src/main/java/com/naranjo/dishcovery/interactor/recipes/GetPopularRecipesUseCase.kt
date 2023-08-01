package com.naranjo.dishcovery.interactor.recipes

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class GetPopularRecipesUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(): List<Recipe> {
        return recipesRepository.getPopularRecipes()
    }
}