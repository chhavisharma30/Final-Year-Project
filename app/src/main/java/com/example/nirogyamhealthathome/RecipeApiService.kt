package com.example.nirogyamhealthathome

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("/v1/recipe")
    fun getRecipes(
        @Query("query") query: String?
    ): Call<List<Recipe>>
}