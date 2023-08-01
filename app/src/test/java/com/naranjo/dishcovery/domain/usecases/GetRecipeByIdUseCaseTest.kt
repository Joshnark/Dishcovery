package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.RecipesRepository
import com.naranjo.dishcovery.interactor.recipes.GetRecipeByIdUseCase
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
class GetRecipeByIdUseCaseTest {

    @Mock
    private lateinit var mockRecipesRepository: RecipesRepository

    @Captor
    private lateinit var idArgumentCaptor: ArgumentCaptor<Int>

    private lateinit var sut: GetRecipeByIdUseCase

    @Before
    fun setup() {
        sut = GetRecipeByIdUseCase(mockRecipesRepository)
    }

    @Test
    fun `On getRecipeById invoked Given success fetching data Returns Recipe`() = runBlocking {
        doReturn(fakeRecipe).`when`(mockRecipesRepository).getRecipeById(idArgumentCaptor.capture() ?: Int.MAX_VALUE)
        val result = sut.invoke(fakeRecipe.id)
        Assert.assertEquals(fakeRecipe, result)
    }

    @Test(expected = Exception::class)
    fun `On getRecipeById invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockRecipesRepository).getRecipeById(any())
        sut.invoke(1)
    }

}