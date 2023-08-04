package com.naranjo.dishcovery.interactor.recipes

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
class SearchRecipesUseCase(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(keyword: String): Result<List<Recipe>> {
        return recipesRepository.searchRecipes(keyword)
    }
}