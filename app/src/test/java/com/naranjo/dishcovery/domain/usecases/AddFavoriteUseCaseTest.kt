package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
import com.naranjo.dishcovery.interactor.favorites.AddFavoriteUseCase
import kotlinx.coroutines.runBlocking
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
class AddFavoriteUseCaseTest {

    @Mock
    private lateinit var mockFavoritesRepository: FavoritesRepository

    @Captor
    private lateinit var recipeArgumentCaptor: ArgumentCaptor<Recipe>

    private lateinit var sut: AddFavoriteUseCase

    @Before
    fun setup() {
        sut = AddFavoriteUseCase(mockFavoritesRepository)
    }

    @Test
    fun `On addFavorite invoked Given success fetching data Executes datasource addFavorite`() = runBlocking {
        doReturn(Unit).`when`(mockFavoritesRepository).addFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)
        sut.invoke(fakeRecipe)
    }

    @Test(expected = Exception::class)
    fun `On addFavorite invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockFavoritesRepository).addFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)
        sut.invoke(fakeRecipe)
    }

}