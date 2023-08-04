package com.naranjo.dishcovery.ui.screens.main.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naranjo.dishcovery.ui.screens.main.models.Category
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeIntent
import com.naranjo.dishcovery.ui.screens.main.pages.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoriesList(selectedCategory: Category, onCategorySelected: (Category) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(Category.values()) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onSelected = {
                    onCategorySelected.invoke(category)
                }
            )
        }
    }
}