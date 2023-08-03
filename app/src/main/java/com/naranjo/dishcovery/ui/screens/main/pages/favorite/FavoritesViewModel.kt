package com.naranjo.dishcovery.ui.screens.main.pages.favorite

import androidx.lifecycle.viewModelScope
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.favorites.GetFavoritesUseCase
import com.naranjo.dishcovery.interactor.recipes.GetPopularRecipesUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipesByCategoryUseCase
import com.naranjo.dishcovery.interactor.recipes.SearchRecipesUseCase
import com.naranjo.dishcovery.ui.screens.base.BaseViewModel
import com.naranjo.dishcovery.ui.screens.main.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): BaseViewModel<FavoritesIntent>() {

    private val _uiState = MutableStateFlow<FavoritesState>(FavoritesState.Idle)
    val uiState: StateFlow<FavoritesState> = _uiState

    private val _changedFavoriteState = MutableSharedFlow<Recipe>()
    val changedFavoriteState: SharedFlow<Recipe> = _changedFavoriteState

    init {
        getRecipes()
    }

    override fun handleIntent(intent: FavoritesIntent): Job {
        return when(intent) {
            is FavoritesIntent.GetFavorites -> getRecipes(intent.load)
            is FavoritesIntent.ChangeFavorite -> changeFavorite(intent.recipe)
        }
    }

    private fun getRecipes(load: Boolean = true) = viewModelScope.launch {
        if (load) {
            _uiState.value = FavoritesState.Loading
        }

        getFavoritesUseCase
            .invoke()
            .onSuccess { _uiState.value = FavoritesState.LoadRecipes(it) }
            .onFailure { _uiState.value = FavoritesState.Error(it) }
    }

    private fun changeFavorite(recipe: Recipe) = viewModelScope.launch {
        changeFavoriteUseCase
            .invoke(recipe)
            .onSuccess { _changedFavoriteState.emit(recipe.copy(isFavorite = it)) }

        getRecipes(false)
    }

}