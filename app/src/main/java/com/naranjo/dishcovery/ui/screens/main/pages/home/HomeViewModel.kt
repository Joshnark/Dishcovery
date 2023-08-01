package com.naranjo.dishcovery.ui.screens.main.pages.home

import androidx.lifecycle.viewModelScope
import com.naranjo.dishcovery.ui.screens.base.BaseViewModel
import com.naranjo.dishcovery.ui.screens.main.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): BaseViewModel() {

    val intent = Channel<HomeIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(HomeState.Idle)
    val state: StateFlow<HomeState> = _state

    private val _selectedCategory = MutableStateFlow(Category.POPULAR)
    val selectedCategory: StateFlow<Category> = _selectedCategory

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when(intent) {
                    is HomeIntent.GetRecipes -> getRecipes(intent.selectedCategory)
                }
            }
        }
    }

    private fun getRecipes(selectedCategory: Category) {
        _selectedCategory.value = selectedCategory
        
        when(selectedCategory) {
            Category.POPULAR -> getPopularRecipes()
            else -> getRecipesByCategory()
        }
    }

    private fun getPopularRecipes() {

    }

    private fun getRecipesByCategory() {

    }

}