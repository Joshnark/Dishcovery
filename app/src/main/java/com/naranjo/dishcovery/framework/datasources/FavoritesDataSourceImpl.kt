package com.naranjo.dishcovery.framework.datasources

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.framework.persistence.FavoriteRecipesDao
import com.naranjo.dishcovery.framework.persistence.RecipeEntity
import com.naranjo.dishcovery.framework.utils.toRecipe
import com.naranjo.dishcovery.framework.utils.toRecipeEntity

class FavoritesDataSourceImpl(
    private val favoriteRecipesDao: FavoriteRecipesDao
): FavoritesDataSource {

    override suspend fun addFavorite(recipe: Recipe) =
        favoriteRecipesDao.insert(recipe.toRecipeEntity())

    override suspend fun removeFavorite(recipe: Recipe) =
        favoriteRecipesDao.delete(recipe.toRecipeEntity())

    override suspend fun getFavorites(): List<Recipe> =
        favoriteRecipesDao.selectAll().map(RecipeEntity::toRecipe)
}