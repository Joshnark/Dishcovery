package com.naranjo.dishcovery.ui.screens.main.pages.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.screens.main.MainViewModel
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchIntent
import com.naranjo.dishcovery.ui.screens.main.pages.search.SearchViewModel
import com.naranjo.dishcovery.ui.screens.main.views.CategoriesList
import com.naranjo.dishcovery.ui.screens.main.views.RecipePreviewItem
import com.naranjo.dishcovery.ui.screens.main.views.RecipesFetchError
import com.naranjo.dishcovery.ui.screens.main.views.RecipesLoading
import com.naranjo.dishcovery.ui.theme.DishCoveryTheme
import com.naranjo.dishcovery.ui.theme.MEDIUM
import com.naranjo.dishcovery.ui.theme.TINY
import com.naranjo.dishcovery.ui.views.SearchTextField
import com.naranjo.dishcovery.ui.views.SmallSpacer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onRecipeTap: (Recipe) -> Unit
) {
    AddedFavoritesLaunchedEffect(viewModel)

    Scaffold(
        modifier = Modifier
            .testTag(stringResource(id = R.string.home_screen_tag))
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

                when(val homeState = state.value) {
                    is HomeState.LoadRecipes -> recipeList(homeState, viewModel, onRecipeTap)
                    is HomeState.Error -> RecipesFetchError()
                    else -> RecipesLoading()
                }
            }
        }
    }
}

@Composable
fun AddedFavoritesLaunchedEffect(viewModel: HomeViewModel) {
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

private fun LazyGridScope.recipeList(
    homeState: HomeState.LoadRecipes,
    viewModel: HomeViewModel,
    onRecipeTap: (Recipe) -> Unit
) {
    items(homeState.recipes) { recipe ->
        val scope = rememberCoroutineScope()

        RecipePreviewItem(
            recipe = recipe,
            onTap = {
                onRecipeTap.invoke(recipe)
            },
            onAddToFavorite = {
                scope.launch {
                    viewModel.intent.send(HomeIntent.ChangeFavorite(recipe = recipe))
                }
            }
        )
    }
}

@Composable
private fun Header(mainViewModel: MainViewModel = viewModel(), searchViewModel: SearchViewModel = viewModel()) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.home_header_title),
            style = MaterialTheme.typography.titleLarge
        )

        SmallSpacer()

        SearchTextField(
            text = String(),
            placeholder = stringResource(id = R.string.search_any_recipe),
            readOnly = true,
            modifier = Modifier.clickable {
                scope.launch {
                    mainViewModel.changePagerToPage(0)
                    searchViewModel.intent.send(SearchIntent.SearchRecipes(load = false))
                }
            }
        )

        SmallSpacer()

        Categories()

        SmallSpacer()

        SelectedCategoryLabel()

        SmallSpacer()
    }
}

@Composable
private fun Categories(viewModel: HomeViewModel = viewModel()) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    CategoriesList(
        selectedCategory = selectedCategory,
        onCategorySelected = { category ->
            scope.launch {
                viewModel.intent.send(HomeIntent.GetRecipes(category))
            }
        }
    )
}

@Composable
private fun SelectedCategoryLabel(viewModel: HomeViewModel = viewModel()) {
    val category = viewModel.selectedCategory.collectAsState()

    Text(
        text = "${stringResource(id = category.value.completeNameResource)} Recipes",
        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
@Preview(device = Devices.NEXUS_7)
private fun PreviewHomeScreen() {
    DishCoveryTheme {
        HomeScreen(onRecipeTap = {})
    }
}