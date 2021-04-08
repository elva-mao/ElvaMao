package com.example.elvamao.service

import com.example.elvamao.data.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("random?apiKey=3cd8e43ff7b74009ad6496f08b4645d4")
    suspend fun getRandomRecipes(@Query("number") pageSize : Int): RecipeResponse

//    @GET("random")
//    fun getRandomRecipes() : RecipeResponse


}