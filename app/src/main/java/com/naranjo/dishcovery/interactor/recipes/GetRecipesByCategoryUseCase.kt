package com.naranjo.dishcovery.interactor.recipes

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class GetRecipesByCategoryUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(category: String): Result<List<Recipe>> {
        return recipesRepository.getRecipesByCategory(category)
    }
}