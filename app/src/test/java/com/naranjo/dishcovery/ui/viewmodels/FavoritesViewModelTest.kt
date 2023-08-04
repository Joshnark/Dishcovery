package com.naranjo.dishcovery.ui.viewmodels

import app.cash.turbine.test
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.favorites.GetFavoritesUseCase
import com.naranjo.dishcovery.interactor.recipes.SearchRecipesUseCase
import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.ui.screens.main.pages.favorite.FavoritesIntent
import com.naranjo.dishcovery.ui.screens.main.pages.favorite.FavoritesState
import com.naranjo.dishcovery.ui.screens.main.pages.favorite.FavoritesViewModel
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchIntent
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchState
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchViewModel
import com.naranjo.dishcovery.ui.utils.MainDispatcherRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Mock
    private lateinit var changeFavoriteUseCase: ChangeFavoriteUseCase

    private lateinit var sut: FavoritesViewModel

    @Before
    fun setup() {
        sut = FavoritesViewModel(
            getFavoritesUseCase,
            changeFavoriteUseCase
        )
    }

    @Test
    fun `On getFavorites intent Given success Then recipe is returned`() = runBlocking {
        val fakeResult = listOf(fakeRecipe)

        doReturn(Result.success(fakeResult)).`when`(getFavoritesUseCase).invoke()

        sut.intent.send(FavoritesIntent.GetFavorites(true))

        assert(sut.uiState.value is FavoritesState.LoadRecipes)
        Assert.assertArrayEquals((sut.uiState.value as FavoritesState.LoadRecipes).recipes.toTypedArray(), fakeResult.toTypedArray())
    }

    @Test
    fun `On getFavorites intent Given error Then returns error`() = runBlocking {
        doReturn(Result.failure<Any>(Exception())).`when`(getFavoritesUseCase).invoke()

        sut.intent.send(FavoritesIntent.GetFavorites(true))

        assert(sut.uiState.value is FavoritesState.Error)
    }

    @Test
    fun `On changeFavorite intent Given success Then recipe with isFavorite true is returned`() = runBlocking {
        doReturn(Result.success(true)).`when`(changeFavoriteUseCase).invoke(any())

        sut.changedFavoriteState.test(5.seconds) {
            sut.intent.send(FavoritesIntent.ChangeFavorite(fakeRecipe))
            Assert.assertEquals(
                fakeRecipe.copy(isFavorite = true),
                awaitItem()
            )
        }
    }

}