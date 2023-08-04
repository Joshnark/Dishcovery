package com.naranjo.dishcovery.data.di

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.data.repositories.FavoritesRepositoryImpl
import com.naranjo.dishcovery.data.repositories.RecipesRepositoryImpl
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesRecipesRepository(recipesDataSource: RecipesDataSource, favoritesDataSource: FavoritesDataSource): RecipesRepository {
        return RecipesRepositoryImpl(recipesDataSource, favoritesDataSource)
    }

    @Provides
    fun providesFavoritesRepository(favoritesDataSource: FavoritesDataSource): FavoritesRepository {
        return FavoritesRepositoryImpl(favoritesDataSource)
    }

}