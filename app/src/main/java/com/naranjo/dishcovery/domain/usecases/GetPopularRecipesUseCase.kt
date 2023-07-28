package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.models.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class GetPopularRecipesUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(): List<Recipe> {
        return recipesRepository.getPopularRecipes()
    }
}