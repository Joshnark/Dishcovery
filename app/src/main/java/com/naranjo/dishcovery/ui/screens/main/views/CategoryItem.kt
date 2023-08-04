package com.naranjo.dishcovery.ui.screens.main.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naranjo.dishcovery.ui.screens.main.models.Category
import com.naranjo.dishcovery.ui.theme.SMALL
import com.naranjo.dishcovery.ui.views.SmallSpacer
import com.naranjo.dishcovery.ui.views.TinySpacer

private const val CATEGORY_IMAGE_SIZE = 27

@Composable
fun CategoryItem(category: Category, isSelected: Boolean, onSelected: () -> Unit) {
    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }

    Row {
        Column(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(SMALL)
                )
                .size(85.dp)
                .clickable { onSelected.invoke() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = category.iconResource),
                modifier = Modifier.size(CATEGORY_IMAGE_SIZE.dp),
                contentDescription = null
            )

            TinySpacer()

            Text(
                text = stringResource(id = category.stringResource),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
        }

        SmallSpacer()
    }
}