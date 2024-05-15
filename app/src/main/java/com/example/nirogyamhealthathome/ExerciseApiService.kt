package com.example.nirogyamhealthathome

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApiService {
    @GET("/v1/exercises")
    fun getExercises(
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("muscle") muscle: String?
    ): Call<List<Exercise>>
}