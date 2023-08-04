package com.naranjo.dishcovery.interactor.recipes

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class GetRecipesUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(): Result<List<Recipe>> {
        return recipesRepository.getRecipes()
    }
}