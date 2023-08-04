package com.naranjo.dishcovery.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipeByIdUseCase
import com.naranjo.dishcovery.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): BaseViewModel<DetailIntent>() {

    private val _uiState = MutableStateFlow<DetailState>(DetailState.Idle)
    val uiState: StateFlow<DetailState> = _uiState

    private val _changedFavoriteState = MutableSharedFlow<Recipe>()
    val changedFavoriteState: SharedFlow<Recipe> = _changedFavoriteState

    override fun handleIntent(intent: DetailIntent): Job {
        return when(intent) {
            is DetailIntent.GetRecipe -> getRecipe(intent.id)
            is DetailIntent.ChangeFavoriteState -> changeFavorite(intent.recipe)
        }
    }

    private fun getRecipe(id: Int) = viewModelScope.launch {
        _uiState.value = DetailState.Loading
        getRecipeByIdUseCase
            .invoke(id)
            .onSuccess {
                _uiState.value = DetailState.LoadRecipe(it)
            }
            .onFailure {
                _uiState.value = DetailState.Error(it)
            }
    }

    private fun changeFavorite(recipe: Recipe) = viewModelScope.launch {
        changeFavoriteUseCase
            .invoke(recipe)
            .onSuccess {
                _changedFavoriteState.emit(recipe.copy(isFavorite = it))
            }
    }
}