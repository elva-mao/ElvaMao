package com.example.elvamao.service

import com.example.elvamao.data.RecipeResponse
import retrofit2.http.GET

interface IApiService {
    @GET("random?apiKey=3cd8e43ff7b74009ad6496f08b4645d4&number=50&tags=vegetarian,dessert")
    suspend fun getRandomRecipes(): RecipeResponse
}