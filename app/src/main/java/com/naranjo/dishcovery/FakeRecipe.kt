package com.naranjo.dishcovery

import com.naranjo.dishcovery.domain.entities.AnalyzedInstruction
import com.naranjo.dishcovery.domain.entities.Equipment
import com.naranjo.dishcovery.domain.entities.Ingredient
import com.naranjo.dishcovery.domain.entities.Length
import com.naranjo.dishcovery.domain.entities.Location
import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.domain.entities.Step

internal val fakeRecipe = Recipe(
    vegetarian = true,
    vegan = true,
    glutenFree = true,
    dairyFree = true,
    veryHealthy = true,
    cheap = false,
    veryPopular = false,
    sustainable = false,
    lowFodmap = false,
    weightWatcherSmartPoints = 12,
    gaps = "no",
    preparationMinutes = -1,
    cookingMinutes = -1,
    aggregateLikes = 309,
    healthScore = 100,
    creditsText = "blogspot.com",
    sourceName = "blogspot.com",
    pricePerServing = 134.63,
    id = 782585,
    title = "Cannellini Bean and Asparagus Salad with Mushrooms",
    readyInMinutes = 45,
    servings = 6,
    sourceUrl = "http://foodandspice.blogspot.com/2016/05/cannellini-bean-and-asparagus-salad.html",
    image = "",
    imageType = "jpg",
    summary = "Cannellini Bean and Asparagus Salad with Mushrooms requires approximately <b>45 minutes</b> from start to finish. This main course has <b>482 calories</b>, <b>31g of protein</b>, and <b>6g of fat</b> per serving. This gluten free, dairy free, lacto ovo vegetarian, and vegan recipe serves 6 and costs <b>$1.35 per serving</b>. 309 people were impressed by this recipe. Head to the store and pick up grain mustard, sea salt, lemon zest, and a few other things to make it today. It is brought to you by foodandspice.blogspot.com. Taking all factors into account, this recipe <b>earns a spoonacular score of 70%</b>, which is pretty good. Similar recipes are <a href=\"https://spoonacular.com/recipes/cannellini-bean-salad-422994\">Cannellini Bean Salad</a>, <a href=\"https://spoonacular.com/recipes/refreshing-cannellini-bean-salad-113127\">Refreshing Cannellini Bean Salad</a>, and <a href=\"https://spoonacular.com/recipes/cannellini-and-green-bean-salad-33177\">Cannellini-and-Green Bean Salad</a>.",
    cuisines = emptyList(),
    dishTypes = listOf("side dish", "lunch", "main course", "salad", "main dish", "dinner"),
    diets = listOf("gluten free", "dairy free", "lacto ovo vegetarian", "vegan"),
    occasions = emptyList(),
    analyzedInstructions = listOf(
        AnalyzedInstruction(
            name = "",
            steps = listOf(
                Step(
                    number = 1,
                    step = "Rinse the cannellini beans and soak for 8 hours or overnight in several inches of water.",
                    ingredients = listOf(
                        Ingredient(10716050, "cannellini beans", "cannellini beans", "cooked-cannellini-beans.png"),
                        Ingredient(14412, "water", "water", "water.png")
                    ),
                    equipment = listOf(
                        Equipment(
                            id = 1,
                            image = "",
                            localizedName = "",
                            name = "Fake equipment"
                        )
                    ),
                    length = Length(480, "minutes")
                ),
                // Add other steps here
            )
        )
    ),
    isFavorite = false,
    location = Location(
        latitude = 1.0,
        longitude = 1.0
    )
)