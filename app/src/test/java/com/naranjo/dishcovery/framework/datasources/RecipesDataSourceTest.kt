package com.naranjo.dishcovery.framework.datasources

import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.framework.network.RecipesApi
import com.naranjo.dishcovery.framework.utils.Response
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
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow

@RunWith(MockitoJUnitRunner::class)
class RecipesDataSourceTest {

    @Mock
    private lateinit var mockRecipesApi: RecipesApi

    @Captor
    private lateinit var idArgumentCaptor: ArgumentCaptor<Int>

    @Captor
    private lateinit var categoryArgumentCaptor: ArgumentCaptor<String>

    private lateinit var sut: RecipesDataSource

    @Before
    fun setup() {
        sut = RecipesDataSourceImpl(mockRecipesApi)
    }

    @Test
    fun `On getRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeResponse = Response(
            data = fakeResult,
            errorMessage = null
        )
        doReturn(fakeResponse).`when`(mockRecipesApi).getRecipes()
        val result = sut.getRecipes()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipes Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesApi).getRecipes()
        sut.getRecipes()
    }

    @Test
    fun `On getRecipeById Given success fetching data Returns Recipe`() = runBlocking {
        val fakeResponse = Response(
            data = fakeRecipe,
            errorMessage = null
        )
        doReturn(fakeResponse).`when`(mockRecipesApi).getRecipeById(idArgumentCaptor.capture() ?: Int.MIN_VALUE)
        val result = sut.getRecipeById(fakeRecipe.id)
        Assert.assertEquals(fakeRecipe, result)
    }

    @Test(expected = Exception::class)
    fun `On getRecipeById Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesApi).getRecipeById(any())
        sut.getRecipeById(Int.MIN_VALUE)
    }

    @Test
    fun `On getPopularRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeResponse = Response(
            data = fakeResult,
            errorMessage = null
        )
        doReturn(fakeResponse).`when`(mockRecipesApi).getRecipes(isPopular = true)
        val result = sut.getPopularRecipes()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getPopularRecipes Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesApi).getRecipes(isPopular = true)
        sut.getPopularRecipes()
    }

    @Test
    fun `On getRecipesByCategory Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeResponse = Response(
            data = fakeResult,
            errorMessage = null
        )
        doReturn(fakeResponse).`when`(mockRecipesApi).getRecipes(isPopular = anyOrNull(), category = categoryArgumentCaptor.capture().orEmpty())
        val result = sut.getRecipesByCategory("1")
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipesByCategory Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesApi).getRecipes(isPopular = anyOrNull(), category = categoryArgumentCaptor.capture().orEmpty())
        sut.getRecipesByCategory("1")
    }

}