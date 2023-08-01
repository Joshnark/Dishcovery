package com.naranjo.dishcovery.framework.network

import com.naranjo.dishcovery.domain.entities.Recipe
import com.naranjo.dishcovery.framework.utils.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesApi {

    @GET(RECIPES_PATH)
    fun getRecipes(
        @Query(CATEGORY_QUERY) category: String? = null,
        @Query(POPULAR_QUERY) isPopular: Boolean? = null
    ): Response<List<Recipe>>

    @GET(RECIPE_PATH)
    fun getRecipeById(
        @Path("id") id: Int
    ): Response<Recipe>

    companion object {
        private const val RECIPES_PATH = "recipes"
        private const val RECIPE_PATH = "recipes/{id}"

        private const val CATEGORY_QUERY = "category"
        private const val POPULAR_QUERY = "popular"
    }

}