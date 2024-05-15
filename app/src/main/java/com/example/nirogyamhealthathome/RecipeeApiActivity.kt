package com.example.nirogyamhealthathome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeeApiActivity : AppCompatActivity() {

    private lateinit var recipeApiService: RecipeApiService
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipee_api)

        // Initialize Retrofit

        val apiKeyInterceptor = ApiKeyInterceptor("cXDSIDoyn/tYPnERXI19Bw==YjPQyBR7NXl7FEB7")

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the ExerciseApiService
        recipeApiService = retrofit.create(RecipeApiService::class.java)

        // Initialize RecyclerView
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView)
        recipeAdapter = RecipeAdapter()
        recipeRecyclerView.adapter = recipeAdapter
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)

        // Add code here for handling user input and making API requests
        val inputIngredient = findViewById<EditText>(R.id.inputIngredient)
        val searchButton2 = findViewById<Button>(R.id.searchButton2)

        searchButton2.setOnClickListener {
            val ingredient = inputIngredient.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                // Make API request
                getRecipes(ingredient)
            } else {
                Toast.makeText(this, "Please enter valid ingredient!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
    private fun getRecipes(ingredient: String) {
        // Make the API call using Retrofit
        val call2 = recipeApiService.getRecipes(ingredient)
        call2.enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    val recipes = response.body()
                    if (recipes != null && recipes.isNotEmpty()) {
                        // Handle the list of exercises
                        // Update RecyclerView with the list of exercises
                        recipeAdapter.setRecipes(recipes)
                    } else {
                        Toast.makeText(this@RecipeeApiActivity, "No recipes found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RecipeeApiActivity, "API request failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Toast.makeText(this@RecipeeApiActivity, "API request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}