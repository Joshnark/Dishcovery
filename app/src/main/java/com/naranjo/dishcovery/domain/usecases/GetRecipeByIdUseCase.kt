package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.models.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class GetRecipeByIdUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(id: Int): Recipe {
        return recipesRepository.getRecipeById(id)
    }
}