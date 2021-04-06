package com.example.elvamao.repository

import android.util.Log
import com.example.elvamao.data.RecipeData
import com.example.elvamao.service.RetrofitClient

class RecipeRepository {
    companion object {
        val TAG = RecipeRepository::class.simpleName
    }

    suspend fun fetchRecipeDataList() : MutableList<RecipeData> {
        val apiService = RetrofitClient.getApiService()
        val recipeList = apiService.getRandomRecipes().recipeList
        //Log.d(TAG, "fetchRecipeDataList | recipeList : $recipeList")
        return recipeList
    }
}