package com.naranjo.dishcovery.framework.datasources

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
import com.naranjo.dishcovery.framework.persistence.FavoriteRecipesDao
import com.naranjo.dishcovery.framework.persistence.RecipeEntity
import com.naranjo.dishcovery.framework.utils.toRecipeEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class FavoritesDatasourceTest {

    @Mock
    private lateinit var favoritesDao: FavoriteRecipesDao

    @Captor
    private lateinit var recipeArgumentCaptor: ArgumentCaptor<RecipeEntity>

    private lateinit var sut: FavoritesDataSource

    @Before
    fun setup() {
        sut = FavoritesDataSourceImpl(favoritesDao)
    }

    @Test
    fun `On addFavorite invoked Given success fetching data Executes datasource addFavorite`() = runBlocking {
        doReturn(Unit).`when`(favoritesDao).insert(recipeArgumentCaptor.capture() ?: fakeRecipe.toRecipeEntity())
        sut.addFavorite(fakeRecipe)
    }

    @Test(expected = Exception::class)
    fun `On addFavorite invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDao).insert(recipeArgumentCaptor.capture() ?: fakeRecipe.toRecipeEntity())
        sut.addFavorite(fakeRecipe)
    }

    @Test
    fun `On removeFavorite invoked Given success fetching data Executes datasource addFavorite`(): Unit = runBlocking {
        doReturn(Unit).`when`(favoritesDao).delete(recipeArgumentCaptor.capture() ?: fakeRecipe.toRecipeEntity())
        sut.removeFavorite(fakeRecipe)
    }

    @Test(expected = Exception::class)
    fun `On removeFavorite invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDao).delete(recipeArgumentCaptor.capture() ?: fakeRecipe.toRecipeEntity())
        sut.addFavorite(fakeRecipe)
    }

    @Test
    fun `On getFavorites invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe.toRecipeEntity() }
        doReturn(fakeResult).`when`(favoritesDao).selectAll()
        val result = sut.getFavorites().map(Recipe::toRecipeEntity)
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getFavorites invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDao).selectAll()
        sut.getFavorites()
    }

}