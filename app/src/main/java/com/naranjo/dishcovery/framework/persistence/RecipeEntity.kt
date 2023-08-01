package com.naranjo.dishcovery.framework.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naranjo.dishcovery.framework.persistence.RecipeEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RecipeEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val cookingMinutes: Int,
    val creditsText: String,
    val dairyFree: Boolean,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Int,
    val image: String,
    val imageType: String,
    val lowFodmap: Boolean,
    val preparationMinutes: Int,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int
) {
    companion object {
        internal const val TABLE_NAME = "recipes"
    }
}