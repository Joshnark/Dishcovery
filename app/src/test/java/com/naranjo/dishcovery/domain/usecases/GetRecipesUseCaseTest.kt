package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.interactor.recipes.GetRecipesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow

@RunWith(MockitoJUnitRunner::class)
class GetRecipesUseCaseTest {

    @Mock
    private lateinit var mockRecipesRepository: RecipesRepository

    private lateinit var sut: GetRecipesUseCase

    @Before
    fun setup() {
        sut = GetRecipesUseCase(mockRecipesRepository)
    }

    @Test
    fun `On getRecipes invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }

        doReturn(Result.success(fakeResult)).`when`(mockRecipesRepository).getRecipes()

        val result = sut.invoke()

        Assert.assertArrayEquals(result.getOrThrow().toTypedArray(), fakeResult.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getRecipes invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesRepository).getRecipes()

        sut.invoke()
    }

}