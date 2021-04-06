package com.example.elvamao.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.spoonacular.com/recipes/"

    private var retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    fun getApiService() : IApiService = retrofit.create(IApiService::class.java)

}