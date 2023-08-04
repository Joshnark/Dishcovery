package com.naranjo.dishcovery.ui.viewmodels

import app.cash.turbine.test
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipeByIdUseCase
import com.naranjo.dishcovery.mocks.fakeRecipe
import com.naranjo.dishcovery.ui.screens.detail.DetailIntent
import com.naranjo.dishcovery.ui.screens.detail.DetailState
import com.naranjo.dishcovery.ui.screens.detail.DetailViewModel
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
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getRecipeByIdUseCase: GetRecipeByIdUseCase

    @Mock
    private lateinit var changeFavoriteUseCase: ChangeFavoriteUseCase

    @Captor
    private lateinit var recipeIdArgumentCaptor: ArgumentCaptor<Int>

    @Captor
    private lateinit var recipeArgumentCaptor: ArgumentCaptor<Recipe>

    private lateinit var sut: DetailViewModel

    @Before
    fun setup() {
        sut = DetailViewModel(
            getRecipeByIdUseCase,
            changeFavoriteUseCase
        )
    }

    @Test
    fun `On getRecipe intent Given success Then recipe is returned`() = runBlocking {
        doReturn(Result.success(fakeRecipe)).`when`(getRecipeByIdUseCase).invoke(recipeIdArgumentCaptor.capture() ?: fakeRecipe.id)

        sut.intent.send(DetailIntent.GetRecipe(fakeRecipe.id))

        assert(sut.uiState.value is DetailState.LoadRecipe)
        Assert.assertEquals(recipeIdArgumentCaptor.value, fakeRecipe.id)
        Assert.assertEquals((sut.uiState.value as DetailState.LoadRecipe).recipe, fakeRecipe)
    }

    @Test
    fun `On getRecipe intent Given failure Then exception is returned`() = runBlocking {
        doReturn(Result.failure<Any>(Exception())).`when`(getRecipeByIdUseCase).invoke(any())

        sut.intent.send(DetailIntent.GetRecipe(fakeRecipe.id))

        assert(sut.uiState.value is DetailState.Error)
    }

    @Test
    fun `On changeFavorite intent Given success Then recipe with isFavorite true is returned`() = runBlocking {
        doReturn(Result.success(true)).`when`(changeFavoriteUseCase).invoke(any())

        sut.changedFavoriteState.test(5.seconds) {
            sut.intent.send(DetailIntent.ChangeFavoriteState(fakeRecipe))
            Assert.assertEquals(
                fakeRecipe.copy(isFavorite = true),
                awaitItem()
            )
        }
    }

    @Test
    fun `On viewModel Given init Then state is idle`() {
        assert(sut.uiState.value == DetailState.Idle)
    }

}