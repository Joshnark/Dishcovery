package com.naranjo.dishcovery.ui.screens.main.pages.search

import androidx.lifecycle.viewModelScope
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.interactor.favorites.ChangeFavoriteUseCase
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
class SearchViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): BaseViewModel<SearchIntent>() {


    private val _uiState = MutableStateFlow<SearchState>(SearchState.Idle)
    val uiState: StateFlow<SearchState> = _uiState

    private val _changedFavoriteState = MutableSharedFlow<Recipe>()
    val changedFavoriteState: SharedFlow<Recipe> = _changedFavoriteState

    private var currentKeyword = ""

    init {
        getRecipes(String())
    }

    override fun handleIntent(intent: SearchIntent): Job {
        cancelCurrentJob()
        return when(intent) {
            is SearchIntent.SearchRecipes -> getRecipes(intent.keyword ?: currentKeyword, intent.load)
            is SearchIntent.ChangeFavorite -> changeFavorite(intent.recipe)
        }
    }

    private fun getRecipes(keyword: String, load: Boolean = true) = viewModelScope.launch {
        currentKeyword = keyword

        if (load) {
            _uiState.value = SearchState.Loading
        }

        searchRecipesUseCase
            .invoke(keyword)
            .onSuccess { _uiState.value = SearchState.LoadRecipes(it) }
            .onFailure { _uiState.value = SearchState.Error(it) }
    }

    private fun changeFavorite(recipe: Recipe) = viewModelScope.launch {
        changeFavoriteUseCase
            .invoke(recipe)
            .onSuccess { _changedFavoriteState.emit(recipe.copy(isFavorite = it)) }

        getRecipes(currentKeyword, false)
    }

}