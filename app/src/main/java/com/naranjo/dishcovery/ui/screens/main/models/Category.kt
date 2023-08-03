package com.naranjo.dishcovery.ui.screens.main.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.naranjo.dishcovery.R

enum class Category(
    val apiName: String,
    @StringRes val stringResource: Int,
    @DrawableRes val iconResource: Int,
    @StringRes val completeNameResource: Int = stringResource
) {
    POPULAR     ("",            R.string.popular,       R.drawable.ic_popular),
    MAIN_COURSE ("main course", R.string.main_course,   R.drawable.ic_main_course,  R.string.main_course_complete),
    BREAKFAST   ("breakfast",   R.string.breakfast,     R.drawable.ic_breakfast),
    DESSERT     ("dessert",     R.string.dessert,       R.drawable.ic_dessert),
    APPETIZER   ("appetizer",   R.string.appetizer,     R.drawable.ic_appetizer),
    SOUP        ("soup",        R.string.soup,          R.drawable.ic_soup),
    SALAD       ("salad",       R.string.salad,         R.drawable.ic_salad),
    DRINK       ("drink",       R.string.drink,         R.drawable.ic_drink),
}