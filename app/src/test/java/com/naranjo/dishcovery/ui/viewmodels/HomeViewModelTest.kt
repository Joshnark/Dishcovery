package com.naranjo.dishcovery.ui.viewmodels

import app.cash.turbine.test
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.recipes.GetPopularRecipesUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipesByCategoryUseCase
import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.ui.screens.main.models.Category
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeIntent
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeState
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeViewModel
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
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getPopularRecipesUseCase: GetPopularRecipesUseCase

    @Mock
    private lateinit var getRecipesByCategoryUseCase: GetRecipesByCategoryUseCase

    @Mock
    private lateinit var changeFavoriteUseCase: ChangeFavoriteUseCase

    @Captor
    private lateinit var categoryArgumentCaptor: ArgumentCaptor<String>

    private lateinit var sut: HomeViewModel

    @Before
    fun setup() {
        sut = HomeViewModel(
            getPopularRecipesUseCase,
            getRecipesByCategoryUseCase,
            changeFavoriteUseCase
        )
    }

    @Test
    fun `On handleIntent intent Given popular category and success Then recipe is returned`() = runBlocking {
        val fakeResult = listOf(fakeRecipe)
        val fakeCategory = Category.POPULAR

        doReturn(Result.success(fakeResult)).`when`(getPopularRecipesUseCase).invoke()

        sut.handleIntent(HomeIntent.GetRecipes(fakeCategory))

        assert(sut.uiState.value is HomeState.LoadRecipes)
        Assert.assertArrayEquals((sut.uiState.value as HomeState.LoadRecipes).recipes.toTypedArray(), fakeResult.toTypedArray())
    }

    @Test
    fun `On getRecipe intent Given popular category and success Then recipe is returned`() = runBlocking {
        val fakeResult = listOf(fakeRecipe)
        val fakeCategory = Category.POPULAR

        doReturn(Result.success(fakeResult)).`when`(getPopularRecipesUseCase).invoke()

        sut.intent.send(HomeIntent.GetRecipes(fakeCategory))

        assert(sut.uiState.value is HomeState.LoadRecipes)
        Assert.assertArrayEquals((sut.uiState.value as HomeState.LoadRecipes).recipes.toTypedArray(), fakeResult.toTypedArray())
    }

    @Test
    fun `On getRecipe intent Given popular category and error Then returns error`() = runBlocking {
        val fakeCategory = Category.POPULAR

        doReturn(Result.failure<Any>(Exception())).`when`(getPopularRecipesUseCase).invoke()

        sut.intent.send(HomeIntent.GetRecipes(fakeCategory))

        assert(sut.uiState.value is HomeState.Error)
    }

    @Test
    fun `On getRecipe intent Given each category and success Then recipe is returned`() = runBlocking {
        Category.values().toMutableList().apply {
            remove(Category.POPULAR)
        }.forEach { fakeCategory ->
            val fakeResult = listOf(fakeRecipe)

            doReturn(Result.success(fakeResult)).`when`(getRecipesByCategoryUseCase).invoke(categoryArgumentCaptor.capture().orEmpty())

            sut.intent.send(HomeIntent.GetRecipes(fakeCategory))

            Assert.assertEquals(categoryArgumentCaptor.value, fakeCategory.apiName)
            Assert.assertArrayEquals((sut.uiState.value as HomeState.LoadRecipes).recipes.toTypedArray(), fakeResult.toTypedArray())
        }
    }

    @Test
    fun `On changeFavorite intent Given success Then recipe with isFavorite true is returned`() = runBlocking {
        doReturn(Result.success(true)).`when`(changeFavoriteUseCase).invoke(any())

        sut.changedFavoriteState.test(5.seconds) {
            sut.intent.send(HomeIntent.ChangeFavorite(fakeRecipe))
            Assert.assertEquals(
                fakeRecipe.copy(isFavorite = true),
                awaitItem()
            )
        }
    }

}