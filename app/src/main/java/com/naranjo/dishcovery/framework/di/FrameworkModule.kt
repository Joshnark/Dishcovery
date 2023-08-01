package com.naranjo.dishcovery.framework.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.naranjo.dishcovery.BuildConfig
import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.data.repositories.RecipesRepositoryImpl
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.framework.datasources.FavoritesDataSourceImpl
import com.naranjo.dishcovery.framework.datasources.RecipesDataSourceImpl
import com.naranjo.dishcovery.framework.network.RecipesApi
import com.naranjo.dishcovery.framework.persistence.AppDatabase
import com.naranjo.dishcovery.framework.persistence.FavoriteRecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    // network

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun providesRecipesApi(retrofit: Retrofit): RecipesApi {
        return retrofit.create(RecipesApi::class.java)
    }

    // persistence

    @Provides
    fun providesDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            application.packageName
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesFavoriteRecipesDao(appDatabase: AppDatabase): FavoriteRecipesDao {
        return appDatabase.favoriteRecipesDao()
    }

    // data sources

    @Provides
    fun providesRecipesDataSource(recipesApi: RecipesApi): RecipesDataSource {
        return RecipesDataSourceImpl(
            recipesApi
        )
    }

    @Provides
    fun providesFavoriteRepository(favoriteRecipesDao: FavoriteRecipesDao): FavoritesDataSource {
        return FavoritesDataSourceImpl(
            favoriteRecipesDao
        )
    }

}