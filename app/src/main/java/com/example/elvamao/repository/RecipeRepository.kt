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
        val RESULT_CODE_SUCCESS = 0
        val PAGE_SIZE = 10
    }


    private val mDatabase : AppDatabase? = AppDatabase.getAppDataBase(context)
    private val mDao : RecipeDao? = mDatabase?.recipeDao()

    suspend fun fetchRecipeDataList() : MutableList<RecipeData> {
        val apiService = RetrofitClient.getApiService()
        val response = apiService.getRandomRecipes(PAGE_SIZE)
        val recipeList = mutableListOf<RecipeData>()
        if (response.retCode == RESULT_CODE_SUCCESS) {
            return response.recipeList
        } else {
            // todo notify ui, show friendly tips to user
        }
         response.recipeList
        return recipeList
    }

    suspend fun loadMoreRecipesFromServer() : MutableList<RecipeData>{
        return fetchRecipeDataList()
    }

    fun loadCollectedRecipesFromDB() : MutableList<RecipeData>? {
       return mDao?.getAllRecipes()
    }

    fun saveRecipeDataToDB(recipeData: RecipeData) {
        if(recipeData.isCollected){
            val  uid = mDao?.insert(recipeData)
            Log.d(TAG, "saveRecipeDataToDB | insert or update recipeData $recipeData, uid $uid")
        }else{
            val  uid = mDao?.delete(recipeData)
            Log.d(TAG, "saveRecipeDataToDB | delete recipeData $recipeData, uid $uid")
        }
    }
}