package com.example.nirogyamhealthathome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val BASE_URL = "https://api.api-ninjas.com"
class ExerciseApiActivity : AppCompatActivity() {

    private lateinit var exerciseApiService: ExerciseApiService
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_api)

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
        exerciseApiService = retrofit.create(ExerciseApiService::class.java)

        // Initialize RecyclerView
        exerciseRecyclerView = findViewById(R.id.exerciseRecyclerView)
        exerciseAdapter = ExerciseAdapter()
        exerciseRecyclerView.adapter = exerciseAdapter
        exerciseRecyclerView.layoutManager = LinearLayoutManager(this)

        // Add code here for handling user input and making API requests
        val inputMuscle = findViewById<EditText>(R.id.inputMuscle)
        val searchButton = findViewById<Button>(R.id.searchButton)

        searchButton.setOnClickListener {
            val muscle = inputMuscle.text.toString().trim()
            if (muscle.isNotEmpty()) {
                // Make API request
                getExercises(muscle)
            } else {
                Toast.makeText(this, "Please enter a muscle, type, or name", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getExercises(muscle: String) {
        // Make the API call using Retrofit
        val call = exerciseApiService.getExercises(null, null, muscle)
        call.enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                if (response.isSuccessful) {
                    val exercises = response.body()
                    if (exercises != null && exercises.isNotEmpty()) {
                        // Handle the list of exercises
                        // Update RecyclerView with the list of exercises
                        exerciseAdapter.setExercises(exercises)
                    } else {
                        Toast.makeText(this@ExerciseApiActivity, "No exercises found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ExerciseApiActivity, "API request failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                Toast.makeText(this@ExerciseApiActivity, "API request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
