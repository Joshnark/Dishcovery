package com.naranjo.dishcovery.ui.screens.main.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

data class PageData(
    val screen: @Composable () -> Unit,
    @DrawableRes val outlineIcon: Int,
    @DrawableRes val selectedIcon: Int
)
