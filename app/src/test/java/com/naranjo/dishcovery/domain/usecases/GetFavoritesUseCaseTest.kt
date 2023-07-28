package com.naranjo.dishcovery.domain.usecases

import com.naranjo.dishcovery.domain.mocks.fakeRecipe
import com.naranjo.dishcovery.domain.repositories.FavoritesRepository
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
class GetFavoritesUseCaseTest {

    @Mock
    private lateinit var mockFavoritesRepository: FavoritesRepository

    private lateinit var sut: GetFavoritesUseCase

    @Before
    fun setup() {
        sut = GetFavoritesUseCase(mockFavoritesRepository)
    }

    @Test
    fun `On getFavorites invoked Given success fetching data Returns Recipe List`() = runBlocking {
        val fakeResult = List(10) { _ -> fakeRecipe }
        doReturn(fakeResult).`when`(mockFavoritesRepository).getFavorites()
        val result = sut.invoke()
        Assert.assertArrayEquals(fakeResult.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = Exception::class)
    fun `On getFavorites invoked Given failure fetching data Throws error`(): Unit = runBlocking {
        doThrow(Exception()).`when`(mockFavoritesRepository).getFavorites()
        sut.invoke()
    }

}