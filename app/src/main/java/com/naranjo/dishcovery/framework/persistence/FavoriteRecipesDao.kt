package com.naranjo.dishcovery.framework.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteRecipesDao {

    @Insert
    suspend fun insert(recipe: RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes")
    suspend fun selectAll(): List<RecipeEntity>

}