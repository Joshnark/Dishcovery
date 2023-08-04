package com.naranjo.dishcovery.screens.framework.persistence

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.naranjo.dishcovery.framework.persistence.AppDatabase
import com.naranjo.dishcovery.framework.persistence.FavoriteRecipesDao
import com.naranjo.dishcovery.framework.utils.toRecipeEntity
import com.naranjo.dishcovery.screens.mocks.fakeRecipe
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesRecipesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var favoriteRecipesDao: FavoriteRecipesDao
    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        favoriteRecipesDao = database.favoriteRecipesDao()
    }

    @Test
    fun whenInsert_givenSuccess_thenReturnsInserted(): Unit = runBlocking {
        favoriteRecipesDao.insert(fakeRecipe.toRecipeEntity())
        val recipe = favoriteRecipesDao.select(fakeRecipe.id)
        Assert.assertEquals(recipe.first(), fakeRecipe.toRecipeEntity())
    }

    @Test
    fun whenDelete_givenSuccess_thenInsertedIsErased(): Unit = runBlocking {
        favoriteRecipesDao.insert(fakeRecipe.toRecipeEntity())
        favoriteRecipesDao.delete(fakeRecipe.toRecipeEntity())
        val recipe = favoriteRecipesDao.select(fakeRecipe.id)
        Assert.assertEquals(recipe.size, 0)
    }

    @Test
    fun whenSelectAll_givenRecipeExists_thenReturnsRecipes(): Unit = runBlocking {
        favoriteRecipesDao.insert(fakeRecipe.toRecipeEntity())
        val recipes = favoriteRecipesDao.selectAll()
        Assert.assertArrayEquals(recipes.toTypedArray(), arrayOf(fakeRecipe.toRecipeEntity()))
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}