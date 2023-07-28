package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
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
class GetPopularRecipesUseCaseTest {

    @Mock
    private lateinit var mockRecipesRepository: RecipesRepository

    private lateinit var sut: GetPopularRecipesUseCase

    @Before
    fun setup() {
        sut = GetPopularRecipesUseCase(mockRecipesRepository)
    }

    @Test
    fun `On getPopularRecipes invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(mockRecipesRepository).getPopularRecipes()
        val result = sut.invoke()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getPopularRecipes invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesRepository).getPopularRecipes()
        sut.invoke()
    }

}
