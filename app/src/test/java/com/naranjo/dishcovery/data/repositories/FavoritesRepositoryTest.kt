package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.mocks.fakeRecipe
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
import org.mockito.internal.verification.Times
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
    fun `On changeFavorite invoked Given success and recipe not in database Executes datasource addFavorite`(): Unit = runBlocking {
        doReturn(emptyList<Recipe>()).`when`(favoritesDataSource).getRecipe(any())
        doReturn(Unit).`when`(favoritesDataSource).addFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)

        val result = sut.changeFavorite(fakeRecipe)

        verify(favoritesDataSource, Times(1)).addFavorite(any())
        verify(favoritesDataSource, Times(0)).removeFavorite(any())
        Assert.assertEquals(recipeArgumentCaptor.value, fakeRecipe)
        assert(result.getOrThrow())
    }

    @Test
    fun `On changeFavorite invoked Given success and recipe in database Executes datasource removeFavorite`(): Unit = runBlocking {
        doReturn(listOf(fakeRecipe)).`when`(favoritesDataSource).getRecipe(any())
        doReturn(Unit).`when`(favoritesDataSource).removeFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)

        val result = sut.changeFavorite(fakeRecipe)

        verify(favoritesDataSource, Times(1)).removeFavorite(any())
        verify(favoritesDataSource, Times(0)).addFavorite(any())
        Assert.assertEquals(recipeArgumentCaptor.value, fakeRecipe)
        assert(result.getOrThrow().not())
    }

    @Test(expected = Exception::class)
    fun `On changeFavorite invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDataSource).addFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)

        val result = sut.changeFavorite(fakeRecipe)

        assert(result.isFailure)
    }

    @Test
    fun `On getFavorites invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe.copy(isFavorite = true) }
        doReturn(fakeResult).`when`(favoritesDataSource).getFavorites()

        val result = sut.getFavorites()

        assert(result.isSuccess)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getFavorites invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(favoritesDataSource).getFavorites()

        val result = sut.getFavorites()

        assert(result.isFailure)
    }

}