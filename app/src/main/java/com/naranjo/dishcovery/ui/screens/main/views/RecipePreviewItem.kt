package com.naranjo.dishcovery.ui.screens.main.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.theme.LARGE
import com.naranjo.dishcovery.ui.theme.SMALL
import com.naranjo.dishcovery.ui.theme.SemiTransparentBlack
import com.naranjo.dishcovery.ui.theme.SemiTransparentBlack50
import com.naranjo.dishcovery.ui.theme.TINY
import com.naranjo.dishcovery.ui.views.SmallSpacer
import com.naranjo.dishcovery.ui.views.TinySpacer

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipePreviewItem(recipe: Recipe, onTap: () -> Unit = {}, onAddToFavorite: () -> Unit = {}) {
    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(SMALL)
                ).clickable {
                    onTap.invoke()
                }
        ) {
            if (recipe.image.isNotEmpty()) {
                GlideImage(
                    model = recipe.image,
                    contentDescription = recipe.sourceUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(SMALL.dp)
                    )
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = SemiTransparentBlack50,
                        shape = RoundedCornerShape(SMALL.dp)
                    )
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SMALL.dp)
            ) {
                RecipePreviewTime(recipe = recipe)

                RecipePreviewLike(
                    recipe = recipe,
                    onAddToFavorite = onAddToFavorite
                )
            }
        }

        TinySpacer()

        Text(
            text = recipe.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Text(
            text = recipe.creditsText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        SmallSpacer()
    }
}


@Composable
fun RecipePreviewTime(recipe: Recipe) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                color = SemiTransparentBlack,
                shape = RoundedCornerShape(LARGE.dp)
            )
            .padding(vertical = TINY.dp, horizontal = SMALL.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_clock),
            modifier = Modifier.size(17.dp),
            contentDescription = recipe.preparationMinutes.toString(),
            colorFilter = ColorFilter.tint(Color.White)
        )

        TinySpacer()

        Text(
            text = "${recipe.preparationMinutes.coerceAtLeast(1)} mins",
            style = TextStyle(fontSize = 13.sp, color = Color.White)
        )
    }
}

@Composable
fun RecipePreviewLike(recipe: Recipe, onAddToFavorite: () -> Unit) {
    val color = if (recipe.isFavorite == true) {
        Color.Red
    } else {
        Color.White
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_like_selected),
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    onAddToFavorite.invoke()
                },
            colorFilter = ColorFilter.tint(color),
            contentDescription = recipe.preparationMinutes.toString()
        )
    }
}