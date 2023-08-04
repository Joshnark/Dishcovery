package com.naranjo.dishcovery.ui.screens.main.pages.search

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.screens.main.views.RecipePreviewItem
import com.naranjo.dishcovery.ui.screens.main.views.RecipesFetchError
import com.naranjo.dishcovery.ui.screens.main.views.RecipesLoading
import com.naranjo.dishcovery.ui.theme.MEDIUM
import com.naranjo.dishcovery.ui.theme.TINY
import com.naranjo.dishcovery.ui.views.SearchTextField
import com.naranjo.dishcovery.ui.views.SmallSpacer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val GRID_CELL_COUNT = 2

@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel(), onRecipeTap: (Recipe) -> Unit) {
    AddedFavoritesLaunchedEffect(viewModel)

    Scaffold(
        modifier = Modifier
            .testTag(stringResource(id = R.string.search_screen_tag))
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            val state = viewModel.uiState.collectAsState()

            LazyVerticalGrid(
                contentPadding = PaddingValues(MEDIUM.dp),
                verticalArrangement = Arrangement.spacedBy(TINY.dp),
                horizontalArrangement = Arrangement.spacedBy(TINY.dp),
                columns = GridCells.Fixed(GRID_CELL_COUNT)
            ) {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Header()
                }

                when(val searchState = state.value) {
                    is SearchState.LoadRecipes -> recipeList(searchState, viewModel, onRecipeTap)
                    is SearchState.Error -> RecipesFetchError()
                    else -> RecipesLoading()
                }
            }
        }
    }
}

@Composable
fun AddedFavoritesLaunchedEffect(viewModel: SearchViewModel) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.changedFavoriteState.collect {
            val text = if (it.isFavorite == true) {
                context.getString(R.string.added_to_favorites)
            } else {
                context.getString(R.string.removed_from_favorites)
            }

            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}

private fun LazyGridScope.recipeList(searchState: SearchState.LoadRecipes, viewModel: SearchViewModel, onRecipeTap: (Recipe) -> Unit) {
    items(searchState.recipes) { recipe ->
        val scope = rememberCoroutineScope()

        RecipePreviewItem(
            recipe = recipe,
            onAddToFavorite = {
                scope.launch {
                    viewModel.intent.send(SearchIntent.ChangeFavorite(recipe = recipe))
                }
            },
            onTap = {
                onRecipeTap.invoke(recipe)
            }
        )
    }
}

@Composable
private fun Header(viewModel: SearchViewModel = viewModel()) {
    var keyword by remember { mutableStateOf(String()) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchTextField(
            text = keyword,
            placeholder = stringResource(id = R.string.search_any_recipe),
            onValueChanged = { newValue ->
                keyword = newValue

                scope.launch {
                    viewModel.intent.send(SearchIntent.SearchRecipes(keyword))
                }
            }
        )

        SmallSpacer()
    }
}