package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(MockitoJUnitRunner::class)
class AddFavoriteUseCaseTest {

    @Mock
    private lateinit var mockFavoritesRepository: FavoritesRepository

    @Captor
    private lateinit var recipeArgumentCaptor: ArgumentCaptor<Recipe>

    private lateinit var sut: ChangeFavoriteUseCase

    @Before
    fun setup() {
        sut = ChangeFavoriteUseCase(mockFavoritesRepository)
    }

    @Test
    fun `On changeFavorite invoked Given success Executes repository changeFavorite and results in success`(): Unit = runBlocking {
        doReturn(true).`when`(mockFavoritesRepository).changeFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)

        val result = sut.invoke(fakeRecipe)

        verify(mockFavoritesRepository, times(1)).changeFavorite(any())
        Assert.assertEquals(fakeRecipe, recipeArgumentCaptor.value)
        assert(result.isSuccess)
        assert(result.getOrThrow())
    }

    @Test(expected = Exception::class)
    fun `On changeFavorite invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockFavoritesRepository).changeFavorite(recipeArgumentCaptor.capture() ?: fakeRecipe)

        sut.invoke(fakeRecipe)
    }

}