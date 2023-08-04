package com.naranjo.dishcovery.ui.screens.main.pages.favorite

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
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = viewModel(), onRecipeTap: (Recipe) -> Unit) {
    AddedFavoritesLaunchedEffect(viewModel)

    Scaffold(
        modifier = Modifier
            .testTag(stringResource(id = R.string.favorite_screen_tag))
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
                columns = GridCells.Fixed(2)
            ) {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Header()
                }

                when(val searchState = state.value) {
                    is FavoritesState.LoadRecipes -> recipeList(searchState, viewModel, onRecipeTap)
                    is FavoritesState.Error -> RecipesFetchError()
                    else -> RecipesLoading()
                }
            }
        }
    }
}

@Composable
fun AddedFavoritesLaunchedEffect(viewModel: FavoritesViewModel) {
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

private fun LazyGridScope.recipeList(searchState: FavoritesState.LoadRecipes, viewModel: FavoritesViewModel, onRecipeTap: (Recipe) -> Unit) {
    items(searchState.recipes) { recipe ->
        val scope = rememberCoroutineScope()

        RecipePreviewItem(
            recipe = recipe,
            onAddToFavorite = {
                scope.launch {
                    viewModel.intent.send(FavoritesIntent.ChangeFavorite(recipe = recipe))
                }
            },
            onTap = {
                onRecipeTap.invoke(recipe)
            }
        )
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.favorites_header_title),
            style = MaterialTheme.typography.titleMedium
        )

        SmallSpacer()
    }
}