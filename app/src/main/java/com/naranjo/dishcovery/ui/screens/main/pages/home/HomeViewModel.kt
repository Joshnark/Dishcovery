package com.naranjo.dishcovery.ui.screens.main.pages.home

import androidx.lifecycle.viewModelScope
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
import com.naranjo.dishcovery.interactor.recipes.GetPopularRecipesUseCase
import com.naranjo.dishcovery.interactor.recipes.GetRecipesByCategoryUseCase
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
class HomeViewModel @Inject constructor(
    private val getPopularRecipesUseCase: GetPopularRecipesUseCase,
    private val getRecipesByCategoryUseCase: GetRecipesByCategoryUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): BaseViewModel<HomeIntent>() {

    private val _uiState = MutableStateFlow<HomeState>(HomeState.Idle)
    val uiState: StateFlow<HomeState> = _uiState

    private val _selectedCategory = MutableStateFlow(Category.POPULAR)
    val selectedCategory: StateFlow<Category> = _selectedCategory

    private val _changedFavoriteState = MutableSharedFlow<Recipe>()
    val changedFavoriteState: SharedFlow<Recipe> = _changedFavoriteState

    init {
        getRecipes(Category.POPULAR)
    }

    override fun handleIntent(intent: HomeIntent): Job {
        cancelCurrentJob()
        return when(intent) {
            is HomeIntent.GetRecipes -> getRecipes(intent.selectedCategory ?: _selectedCategory.value, intent.load)
            is HomeIntent.ChangeFavorite -> changeFavorite(intent.recipe)
        }
    }

    private fun getRecipes(selectedCategory: Category, load: Boolean = true): Job {
        _selectedCategory.value = selectedCategory

        if (load) {
            _uiState.value = HomeState.Loading
        }

        return when(selectedCategory) {
            Category.POPULAR -> getPopularRecipes()
            else -> getRecipesByCategory(selectedCategory)
        }
    }

    private fun getPopularRecipes() = viewModelScope.launch {
        getPopularRecipesUseCase
            .invoke()
            .onSuccess { _uiState.value = HomeState.LoadRecipes(it) }
            .onFailure { _uiState.value = HomeState.Error(it) }
    }

    private fun getRecipesByCategory(category: Category) = viewModelScope.launch {
        getRecipesByCategoryUseCase
            .invoke(category.apiName)
            .onSuccess { _uiState.value = HomeState.LoadRecipes(it) }
            .onFailure { _uiState.value = HomeState.Error(it) }
    }

    private fun changeFavorite(recipe: Recipe) = viewModelScope.launch {
        changeFavoriteUseCase
            .invoke(recipe)
            .onSuccess { _changedFavoriteState.emit(recipe.copy(isFavorite = it)) }
        getRecipes(_selectedCategory.value, false)
    }

}