package com.naranjo.dishcovery.interactor.recipes

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository

class GetRecipeByIdUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(id: Int): Recipe {
        return recipesRepository.getRecipeById(id)
    }
}