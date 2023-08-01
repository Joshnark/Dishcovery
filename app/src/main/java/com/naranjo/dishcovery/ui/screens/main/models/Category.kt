package com.naranjo.dishcovery.ui.screens.main.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.naranjo.dishcovery.R

enum class Category(
    @StringRes val stringResource: Int,
    @DrawableRes val iconResource: Int,
    @StringRes val completeNameResource: Int = stringResource
) {
    POPULAR(R.string.popular, R.drawable.ic_popular),
    MAIN_COURSE(R.string.main_course, R.drawable.ic_main_course, R.string.main_course_complete),
    BREAKFAST(R.string.breakfast, R.drawable.ic_breakfast),
    DESSERT(R.string.dessert, R.drawable.ic_dessert),
    APPETIZER(R.string.appetizer, R.drawable.ic_appetizer),
    SOUP(R.string.soup, R.drawable.ic_soup),
    SALAD(R.string.salad, R.drawable.ic_salad),
    DRINK(R.string.drink, R.drawable.ic_drink),
}