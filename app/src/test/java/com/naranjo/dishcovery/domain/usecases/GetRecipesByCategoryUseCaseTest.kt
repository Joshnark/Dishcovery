package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.interactor.recipes.GetRecipesByCategoryUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow

@RunWith(MockitoJUnitRunner::class)
class GetRecipesByCategoryUseCaseTest {

    @Mock
    private lateinit var mockRecipesRepository: RecipesRepository

    @Captor
    private lateinit var categoryArgumentCaptor: ArgumentCaptor<String>

    private lateinit var sut: GetRecipesByCategoryUseCase

    @Before
    fun setup() {
        sut = GetRecipesByCategoryUseCase(mockRecipesRepository)
    }

    @Test
    fun `On getRecipesByCategory invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        val fakeCategory = "elevenses"

        doReturn(Result.success(fakeResult)).`when`(mockRecipesRepository).getRecipesByCategory(categoryArgumentCaptor.capture().orEmpty())

        val result = sut.invoke(fakeCategory)

        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
        Assert.assertEquals(categoryArgumentCaptor.value, fakeCategory)
    }

    @Test(expected = Exception::class)
    fun `On getRecipesByCategory invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesRepository).getRecipesByCategory(categoryArgumentCaptor.capture().orEmpty())

        sut.invoke("1")
    }

}