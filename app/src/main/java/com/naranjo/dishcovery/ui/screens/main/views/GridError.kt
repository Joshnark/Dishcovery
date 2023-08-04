package com.naranjo.dishcovery.ui.screens.main.views

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import com.naranjo.dishcovery.ui.views.ErrorView

fun LazyGridScope.RecipesFetchError() {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        ErrorView()
    }
}