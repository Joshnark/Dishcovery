package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
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

@RunWith(MockitoJUnitRunner::class)
class RecipesRepositoryTest {

    @Mock
    private lateinit var mockRecipesDataSource: RecipesDataSource

    @Captor
    private lateinit var idArgumentCaptor: ArgumentCaptor<Int>

    private lateinit var sut: RecipesRepository

    @Before
    fun setup() {
        sut = RecipesRepositoryImpl(mockRecipesDataSource)
    }

    @Test
    fun `On getRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(mockRecipesDataSource).getRecipes()
        val result = sut.getRecipes()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipes Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getRecipes()
        sut.getRecipes()
    }

    @Test
    fun `On getRecipeById Given success fetching data Returns Recipe`() = runBlocking {
        doReturn(fakeRecipe).`when`(mockRecipesDataSource).getRecipeById(idArgumentCaptor.capture() ?: Int.MIN_VALUE)
        val result = sut.getRecipeById(fakeRecipe.id)
        Assert.assertEquals(fakeRecipe, result)
    }

    @Test(expected = Exception::class)
    fun `On getRecipeById Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getRecipeById(any())
        sut.getRecipeById(Int.MIN_VALUE)
    }

    @Test
    fun `On getPopularRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(mockRecipesDataSource).getPopularRecipes()
        val result = sut.getPopularRecipes()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getPopularRecipes Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getPopularRecipes()
        sut.getPopularRecipes()
    }

    @Test
    fun `On getRecipesByCategory Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(mockRecipesDataSource).getRecipesByCategory()
        val result = sut.getRecipesByCategory()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipesByCategory Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getRecipesByCategory()
        sut.getRecipesByCategory()
    }

}