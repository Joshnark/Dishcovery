package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
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
class FavoritesRepositoryTest {

    @Mock
    private lateinit var favoritesDataSource: FavoritesDataSource

    @Captor
    private lateinit var recipeArgumentCaptor: ArgumentCaptor<Recipe>

    private lateinit var sut: FavoritesRepository

    @Before
    fun setup() {
        sut = FavoritesRepositoryImpl(favoritesDataSource)
    }

    @Test
    fun `On changeFavorite invoked Given success fetching data Executes datasource addFavorite`(): Unit = runBlocking {
        doReturn(Unit).`when`(favoritesDataSource).addFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)
        sut.changeFavorite(fakeRecipe)
    }

    @Test(expected = Exception::class)
    fun `On changeFavorite invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDataSource).addFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)
        sut.changeFavorite(fakeRecipe)
    }


    @Test
    fun `On getFavorites invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(favoritesDataSource).getFavorites()
        val result = sut.getFavorites()
        Assert.assertEquals(Result.success(fakeResult), result)
    }

    @Test(expected = Exception::class)
    fun `On getFavorites invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDataSource).getFavorites()
        sut.getFavorites()
    }

}