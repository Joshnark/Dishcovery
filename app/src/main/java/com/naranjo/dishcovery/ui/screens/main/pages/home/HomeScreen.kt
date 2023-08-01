package com.naranjo.dishcovery.ui.screens.main.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naranjo.dishcovery.fakeRecipe
import com.naranjo.dishcovery.ui.screens.main.models.Category
import com.naranjo.dishcovery.ui.screens.main.views.CategoryItem
import com.naranjo.dishcovery.ui.screens.main.views.RecipePreviewItem
import com.naranjo.dishcovery.ui.theme.DishCoveryTheme
import com.naranjo.dishcovery.ui.theme.MEDIUM
import com.naranjo.dishcovery.ui.theme.TINY
import com.naranjo.dishcovery.ui.views.SearchTextField
import com.naranjo.dishcovery.ui.views.SmallSpacer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val fake = List(5) { fakeRecipe }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) { padding ->

        LazyVerticalGrid(
            modifier = Modifier
                .padding(padding),
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

            items(fake) { recipe ->
                RecipePreviewItem(
                    recipe = recipe
                )
            }
        }
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "What would you like \nto cook today?",
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
        )

        SmallSpacer()

        SearchTextField(
            placeholder = "Search any recipe!",
            modifier = Modifier.clickable {

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
fun SelectedCategoryLabel(viewModel: HomeViewModel = viewModel()) {
    val category = viewModel.selectedCategory.collectAsState()

    Text(
        text = "${stringResource(id = category.value.completeNameResource)} Recipes",
        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun Categories(viewModel: HomeViewModel = viewModel()) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(Category.values()) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onSelected = {
                    scope.launch {
                        viewModel.intent.send(HomeIntent.GetRecipes(category))
                    }
                }
            )
        }
    }
}

@Composable
@Preview(device = Devices.NEXUS_7)
private fun PreviewHomeScreen() {
    DishCoveryTheme {
        HomeScreen()
    }
}