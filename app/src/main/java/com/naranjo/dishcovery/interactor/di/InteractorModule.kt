package com.naranjo.dishcovery.interactor.di

import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.favorites.GetFavoritesUseCase
import com.naranjo.dishcovery.interactor.recipes.GetPopularRecipesUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipeByIdUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipesByCategoryUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipesUseCase
import com.naranjo.dishcovery.interactor.recipes.SearchRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Provides
    fun providesGetPopularRecipesUseCase(recipesRepository: RecipesRepository) =
        GetPopularRecipesUseCase(recipesRepository)

    @Provides
    fun providesGetRecipeByIdUseCase(recipesRepository: RecipesRepository) =
        GetRecipeByIdUseCase(recipesRepository)

    @Provides
    fun providesGetRecipesByCategoryUseCase(recipesRepository: RecipesRepository) =
        GetRecipesByCategoryUseCase(recipesRepository)

    @Provides
    fun providesGetRecipesUseCase(recipesRepository: RecipesRepository) =
        GetRecipesUseCase(recipesRepository)

    @Provides
    fun providesSearchRecipeUseCase(recipesRepository: RecipesRepository) =
        SearchRecipesUseCase(recipesRepository)

    @Provides
    fun providesAddFavoriteUseCase(favoritesRepository: FavoritesRepository) =
        ChangeFavoriteUseCase(favoritesRepository)

    @Provides
    fun providesGetFavoritesUseCase(favoritesRepository: FavoritesRepository) =
        GetFavoritesUseCase(favoritesRepository)

}