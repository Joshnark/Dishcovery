package com.naranjo.dishcovery.ui.screens.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.ui.theme.SMALL
import com.naranjo.dishcovery.ui.views.SmallSpacer
import com.naranjo.dishcovery.ui.views.TinySpacer

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipePreviewItem(recipe: Recipe) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(SMALL)
                )
        ) {
            if (recipe.image.isNotEmpty()) {
                GlideImage(
                    model = recipe.image,
                    contentDescription = recipe.sourceUrl
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SMALL.dp)
            ) {
                RecipePreviewTime(recipe = recipe)

                RecipePreviewRate(recipe = recipe)
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
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            modifier = Modifier.size(20.dp),
            contentDescription = recipe.preparationMinutes.toString()
        )

        TinySpacer()

        Text(
            text = "${recipe.preparationMinutes} mins",
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

@Composable
fun RecipePreviewRate(recipe: Recipe) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            modifier = Modifier.size(35.dp),
            contentDescription = recipe.preparationMinutes.toString()
        )
    }
}