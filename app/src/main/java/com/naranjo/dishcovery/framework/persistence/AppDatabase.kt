package com.naranjo.dishcovery.framework.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteRecipesDao(): FavoriteRecipesDao
}

