package com.naranjo.dishcovery.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.naranjo.dishcovery.ui.theme.SemiTransparentBlack
import com.naranjo.dishcovery.ui.theme.SemiTransparentBlack50

@Composable
fun GradientBox() {
    val gradientBottom = Brush.verticalGradient(
        colors = listOf(Color.Transparent, SemiTransparentBlack),
    )

    val gradientTop = Brush.verticalGradient(
        colors = listOf(SemiTransparentBlack50, Color.Transparent),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBottom)
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientTop)
        )
    }
}