package com.naranjo.dishcovery.data.repositories

import com.naranjo.dishcovery.data.datasources.FavoritesDataSource
import com.naranjo.dishcovery.data.datasources.RecipesDataSource
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.mocks.fakeRecipe
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
import org.mockito.kotlin.verifyNoInteractions

@RunWith(MockitoJUnitRunner::class)
class RecipesRepositoryTest {

    @Mock
    private lateinit var mockRecipesDataSource: RecipesDataSource

    @Mock
    private lateinit var mockFavoritesDataSource: FavoritesDataSource

    @Captor
    private lateinit var idArgumentCaptor: ArgumentCaptor<Int>

    @Captor
    private lateinit var categoryArgumentCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var keywordArgumentCaptor: ArgumentCaptor<String>

    private lateinit var sut: RecipesRepository

    @Before
    fun setup() {
        sut = RecipesRepositoryImpl(mockRecipesDataSource, mockFavoritesDataSource)
    }

    @Test
    fun `On getRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }

        doReturn(fakeResult).`when`(mockRecipesDataSource).getRecipes()
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getRecipes()

        assert(result.isSuccess)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

    @Test
    fun `On getRecipes Given success fetching and them added to favorites data Returns correct Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe.copy(isFavorite = true) }

        doReturn(fakeResult).`when`(mockRecipesDataSource).getRecipes()
        doReturn(fakeResult).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getRecipes()

        assert(result.isSuccess)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipes Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getRecipes()
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getRecipes()

        assert(result.isFailure)
        verifyNoInteractions(mockFavoritesDataSource)
    }

    @Test
    fun `On getRecipeById Given success fetching data Returns Recipe`() = runBlocking {
        doReturn(fakeRecipe).`when`(mockRecipesDataSource).getRecipeById(idArgumentCaptor.capture() ?: Int.MIN_VALUE)

        val result = sut.getRecipeById(fakeRecipe.id)

        Assert.assertEquals(idArgumentCaptor.value, fakeRecipe.id)
        Assert.assertEquals(result.getOrThrow(), fakeRecipe)
        assert(result.isSuccess)
    }

    @Test(expected = Exception::class)
    fun `On getRecipeById Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getRecipeById(any())

        val result = sut.getRecipeById(Int.MIN_VALUE)

        assert(result.isFailure)
    }

    @Test
    fun `On getPopularRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(mockRecipesDataSource).getPopularRecipes()
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getPopularRecipes()

        assert(result.isSuccess)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getPopularRecipes Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getPopularRecipes()
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getPopularRecipes()

        assert(result.isFailure)
        verifyNoInteractions(mockFavoritesDataSource)
    }

    @Test
    fun `On getRecipesByCategory Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeCategory = "elevenses"

        doReturn(fakeResult).`when`(mockRecipesDataSource).getRecipesByCategory(categoryArgumentCaptor.capture().orEmpty())
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getRecipesByCategory(fakeCategory)

        assert(result.isSuccess)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
        Assert.assertEquals(categoryArgumentCaptor.value, fakeCategory)
    }

    @Test(expected = Exception::class)
    fun `On getRecipesByCategory Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesDataSource).getRecipesByCategory(categoryArgumentCaptor.capture().orEmpty())
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.getRecipesByCategory("1")

        assert(result.isFailure)
    }


    @Test
    fun `On searchRecipes Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeKeyword = "chicken"

        doReturn(fakeResult).`when`(mockRecipesDataSource).searchRecipes(keywordArgumentCaptor.capture().orEmpty())
        doReturn(emptyList<Recipe>()).`when`(mockFavoritesDataSource).getFavorites()

        val result = sut.searchRecipes(fakeKeyword)

        assert(result.isSuccess)
        Assert.assertEquals(keywordArgumentCaptor.value, fakeKeyword)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

}