package com.naranjo.dishcovery.ui.viewmodels

import app.cash.turbine.test
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.recipes.SearchRecipesUseCase
import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.ui.screens.main.models.Category
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeIntent
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeState
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
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var searchRecipesUseCase: SearchRecipesUseCase

    @Mock
    private lateinit var changeFavoriteUseCase: ChangeFavoriteUseCase

    @Captor
    private lateinit var keywordArgumentCaptor: ArgumentCaptor<String>

    private lateinit var sut: SearchViewModel

    @Before
    fun setup() {
        sut = SearchViewModel(
            searchRecipesUseCase,
            changeFavoriteUseCase
        )
    }

    @Test
    fun `On searchRecipe intent Given success Then recipe is returned`() = runBlocking {
        val fakeResult = listOf(fakeRecipe)
        val fakeKeyword = "chicken"

        doReturn(Result.success(fakeResult)).`when`(searchRecipesUseCase).invoke(keywordArgumentCaptor.capture().orEmpty())

        sut.intent.send(SearchIntent.SearchRecipes(fakeKeyword))

        assert(sut.uiState.value is SearchState.LoadRecipes)
        Assert.assertEquals(keywordArgumentCaptor.value, fakeKeyword)
        Assert.assertArrayEquals((sut.uiState.value as SearchState.LoadRecipes).recipes.toTypedArray(), fakeResult.toTypedArray())
    }

    @Test
    fun `On searchRecipe intent Given error Then returns error`() = runBlocking {
        val fakeKeyword = "chicken"

        doReturn(Result.failure<Any>(Exception())).`when`(searchRecipesUseCase).invoke(any())

        sut.intent.send(SearchIntent.SearchRecipes(fakeKeyword))

        assert(sut.uiState.value is SearchState.Error)
    }

    @Test
    fun `On changeFavorite intent Given success Then recipe with isFavorite true is returned`() = runBlocking {
        doReturn(Result.success(true)).`when`(changeFavoriteUseCase).invoke(any())

        sut.changedFavoriteState.test(5.seconds) {
            sut.intent.send(SearchIntent.ChangeFavorite(fakeRecipe))
            Assert.assertEquals(
                fakeRecipe.copy(isFavorite = true),
                awaitItem()
            )
        }
    }

}