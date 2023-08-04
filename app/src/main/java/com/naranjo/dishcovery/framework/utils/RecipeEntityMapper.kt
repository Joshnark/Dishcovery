package com.naranjo.dishcovery.framework.utils

import com.naranjo.dishcovery.domain.entities.Location
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.framework.persistence.RecipeEntity

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        aggregateLikes = this.aggregateLikes,
        cheap = this.cheap,
        cookingMinutes = this.cookingMinutes,
        creditsText = this.creditsText,
        dairyFree = this.dairyFree,
        gaps = this.gaps,
        glutenFree = this.glutenFree,
        healthScore = this.healthScore,
        image = this.image,
        imageType = this.imageType,
        lowFodmap = this.lowFodmap,
        preparationMinutes = this.preparationMinutes,
        pricePerServing = this.pricePerServing,
        readyInMinutes = this.readyInMinutes,
        servings = this.servings,
        sourceName = this.sourceName,
        sourceUrl = this.sourceUrl,
        summary = this.summary,
        sustainable = this.sustainable,
        vegan = this.vegan,
        vegetarian = this.vegetarian,
        veryHealthy = this.veryHealthy,
        veryPopular = this.veryPopular,
        weightWatcherSmartPoints = this.weightWatcherSmartPoints,
        analyzedInstructions = emptyList(),
        cuisines = emptyList(),
        diets = emptyList(),
        dishTypes = emptyList(),
        occasions = emptyList(),
        location = Location(.0,.0),
        isFavorite = false
    )
}

fun Recipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        title = this.title,
        aggregateLikes = this.aggregateLikes,
        cheap = this.cheap,
        cookingMinutes = this.cookingMinutes,
        creditsText = this.creditsText,
        dairyFree = this.dairyFree,
        gaps = this.gaps,
        glutenFree = this.glutenFree,
        healthScore = this.healthScore,
        image = this.image,
        imageType = this.imageType,
        lowFodmap = this.lowFodmap,
        preparationMinutes = this.preparationMinutes,
        pricePerServing = this.pricePerServing,
        readyInMinutes = this.readyInMinutes,
        servings = this.servings,
        sourceName = this.sourceName,
        sourceUrl = this.sourceUrl,
        summary = this.summary,
        sustainable = this.sustainable,
        vegan = this.vegan,
        vegetarian = this.vegetarian,
        veryHealthy = this.veryHealthy,
        veryPopular = this.veryPopular,
        weightWatcherSmartPoints = this.weightWatcherSmartPoints
    )
}