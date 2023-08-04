package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.interactor.recipes.SearchRecipesUseCase
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
class SearchRecipesUseCaseTest {

    @Mock
    private lateinit var mockRecipesRepository: RecipesRepository

    @Captor
    private lateinit var keywordArgumentCaptor: ArgumentCaptor<String>

    private lateinit var sut: SearchRecipesUseCase

    @Before
    fun setup() {
        sut = SearchRecipesUseCase(mockRecipesRepository)
    }

    @Test
    fun `On searchRecipes invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeKeyword = "chicken"

        doReturn(Result.success(fakeResult)).`when`(mockRecipesRepository).searchRecipes(keywordArgumentCaptor.capture().orEmpty())

        val result = sut.invoke(fakeKeyword)

        Assert.assertEquals(keywordArgumentCaptor.value, fakeKeyword)
        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipes invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        val fakeKeyword = "chicken"

        doThrow(Exception()).`when`(mockRecipesRepository).searchRecipes(any())

        sut.invoke(fakeKeyword)
    }

}