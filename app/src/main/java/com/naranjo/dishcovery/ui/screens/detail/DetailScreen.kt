package com.naranjo.dishcovery.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.extensions.responsive
import com.naranjo.dishcovery.ui.theme.MEDIUM
import com.naranjo.dishcovery.ui.theme.SMALL
import com.naranjo.dishcovery.ui.views.ErrorView
import com.naranjo.dishcovery.ui.views.HtmlText
import com.naranjo.dishcovery.ui.views.LargeSpacer
import com.naranjo.dishcovery.ui.views.LoadingView
import com.naranjo.dishcovery.ui.views.MediumSpacer
import com.naranjo.dishcovery.ui.views.SmallSpacer
import com.naranjo.dishcovery.ui.views.TinySpacer
import kotlinx.coroutines.launch

private const val RECIPE_NAME_SIZE = 18
private const val RECIPE_SOURCE_SIZE = 14

@Composable
fun DetailScreen(detailViewModel: DetailViewModel = viewModel()) {
    val state = detailViewModel.uiState.collectAsState()

    Scaffold { padding ->
        Box(
            modifier = Modifier.padding(padding),
        ) {
           when(val value = state.value) {
               is DetailState.LoadRecipe -> DetailRecipe(recipe = value.recipe)
               is DetailState.Error -> ErrorView()
               else -> LoadingView()
           }
        }
    }
}

@Composable
private fun DetailRecipe(recipe: Recipe, viewModel: DetailViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(recipe)

            Body(recipe)
        }

        Button(
            onClick = {
                scope.launch {
                    viewModel.navigate(DetailFragmentDirections.actionDetailFragmentToMapFragment(
                        longitude = recipe.location?.longitude.toString(),
                        latitude = recipe.location?.latitude.toString()
                    ))
                }
            }
        ) {
            Text(stringResource(id = R.string.open_map))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Header(recipe: Recipe) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (recipe.image.isNotEmpty()) {
            GlideImage(
                model = recipe.image,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop
            )
        } else {
            Spacer(modifier = Modifier.size(1.dp))
        }
    }
}

@Composable
private fun Body(recipe: Recipe) {
    val ingredients = recipe.analyzedInstructions.first().steps.flatMap {
        it.ingredients
    }.distinct()

    val equipments = recipe.analyzedInstructions.first().steps.flatMap {
        it.equipment
    }.distinct()

    Column(
        modifier = Modifier
            .responsive()
            .background(MaterialTheme.colorScheme.surface)
            .padding(MEDIUM.dp),
    ) {
        LargeSpacer()

        Text(
            text = recipe.title,
            style = TextStyle(fontSize = RECIPE_NAME_SIZE.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        )
        TinySpacer()
        Text(
            text = stringResource(id = R.string.recipe_source_by, recipe.sourceName),
            style = TextStyle(fontSize = RECIPE_SOURCE_SIZE.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        MediumSpacer()

        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.titleSmall
        )
        TinySpacer()
        HtmlText(
            text = recipe.summary
        )

        MediumSpacer()

        if (ingredients.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.ingredients),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            )
            SmallSpacer()
            Column {
                ingredients.forEachIndexed { index, ingredient ->
                    RecipeElement(index = index, name = ingredient.name, ingredients.lastIndex == index)
                }
            }
        }

        MediumSpacer()

        if(equipments.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.equipment),
                style = MaterialTheme.typography.titleSmall
            )
            SmallSpacer()
            Column {
                equipments.forEachIndexed { index, equipment ->
                    RecipeElement(index = index, name = equipment.name, equipments.lastIndex == index)
                }
            }
        }

        MediumSpacer()

        Text(
            text = stringResource(id = R.string.steps),
            style = MaterialTheme.typography.titleSmall
        )
        SmallSpacer()
        Column {
            recipe.analyzedInstructions.first().steps.forEachIndexed { index, step ->
                RecipeElement(index = index, name = step.step, isLast = recipe.analyzedInstructions.first().steps.lastIndex == index)
            }
        }

        LargeSpacer()
    }
}

private const val RECIPE_ELEMENT_TEXT_SIZE = 14
private const val SEPARATOR_SIZE = 1

@Composable
private fun RecipeElement(index: Int, name: String, isLast: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(SMALL.dp)
                    .size(MEDIUM.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (index + 1).toString(),
                    style = TextStyle(fontSize = RECIPE_ELEMENT_TEXT_SIZE.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                )
            }

            SmallSpacer()

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = name,
                    style = TextStyle(fontSize = RECIPE_ELEMENT_TEXT_SIZE.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
                )
            }
        }

        if (isLast.not()) {
            SmallSpacer()

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .height(SEPARATOR_SIZE.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }

    SmallSpacer()
}

@Preview
@Composable
fun PreviewRecipeElement() {
    RecipeElement(index = 10, name = "Ingredient or equipment", isLast = false)
}