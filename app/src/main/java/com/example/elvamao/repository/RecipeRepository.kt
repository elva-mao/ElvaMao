package com.example.elvamao.repository

import android.content.Context
import android.util.Log
import com.example.elvamao.dao.AppDatabase
import com.example.elvamao.dao.RecipeDao
import com.example.elvamao.data.RecipeData
import com.example.elvamao.service.RetrofitClient

class RecipeRepository(context : Context?) {
    companion object {
        val TAG = RecipeRepository::class.simpleName
    }


    private val mDatabase : AppDatabase? = AppDatabase.getAppDataBase(context)
    private val mDao : RecipeDao? = mDatabase?.recipeDao()

    suspend fun fetchRecipeDataList() : MutableList<RecipeData> {
        val apiService = RetrofitClient.getApiService()
        return apiService.getRandomRecipes().recipeList
    }

    suspend fun loadCollectedRecipesFromDB() : MutableList<RecipeData>? {
       return mDao?.getAllRecipes()
    }

    suspend fun saveRecipeDataToDB(recipeData: RecipeData) {
        mDao?.update(recipeData)
        Log.d(TAG, "saveRecipeDataToDB | recipe $recipeData")
    }
}