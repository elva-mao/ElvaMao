package com.example.elvamao.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elvamao.data.RecipeData
import com.example.elvamao.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipeViewModel : ViewModel() {
    companion object {
        val TAG = RecipeViewModel::class.simpleName
    }
    private var mContext : Context? = null
    private val mRecipeRepository: RecipeRepository by lazy { RecipeRepository(mContext) }
    /**
     * live data stores recipe data List
     */
    private var mRecommendRecipeListLiveData : MutableLiveData<MutableList<RecipeData>> = MutableLiveData<MutableList<RecipeData>>()
    private val mCollectedRecipesLiveData = MutableLiveData<MutableList<RecipeData>>().apply {
        value = mutableListOf()
    }
    private var mViewModelJob = Job()
    private val mCoroutineScope = CoroutineScope( mViewModelJob + Dispatchers.Main )

    fun setContext(context : Context?) {
        mContext = context
    }

    fun getRecommendRecipesLiveData(): MutableLiveData<MutableList<RecipeData>> {
        loadRecipeDataList()
        Log.d(TAG,"getRecommendRecipesLiveData")
        return mRecommendRecipeListLiveData
    }

    private fun loadRecipeDataList() {
        mCoroutineScope.launch(Dispatchers.Default){
            var recipeDataList = mRecipeRepository.fetchRecipeDataList()
            try {
                //process the real rsp data, add some fake data, like collected count and share count
                for(recipe in recipeDataList) {
                    recipe.initFakeData()
                }
                Log.d(TAG,"loadRecipeDataList | recipeDataList $recipeDataList")
                mRecommendRecipeListLiveData.postValue(recipeDataList)
            }catch (e : Exception) {
                Log.d(TAG,"loadRecipeDataList | exception ${e.message}")
            }
        }
    }

    fun loadMoreRecommendRecipes() {
        mCoroutineScope.launch (Dispatchers.Default) {
            var recipeDataList = mRecipeRepository.loadMoreRecipesFromServer()
            try {
                Log.d(TAG,"loadMoreRecommendRecipes | load more recipes size : ${recipeDataList.size}")
                val newRecipes = mRecommendRecipeListLiveData.value?.apply {
                    addAll(recipeDataList)
                }
                Log.d(TAG,"loadMoreRecommendRecipes | new recipes size : ${newRecipes?.size}")
                mRecommendRecipeListLiveData.postValue(newRecipes)
            }catch (e : Exception) {
                Log.d(TAG,"loadMoreRecommendRecipes | exception ${e.message}")
            }
        }
    }

    fun refreshRecommendRecipes() {
        loadRecipeDataList()
        Log.d(TAG,"refreshRecommendRecipes")
    }

    /**
     * handle collect event
     * i. update recipe data to DB
     * ii. update collectLiveData to ensure data consistency and refresh related ui
     */
    fun saveCollectedRecipeToDB(recipeData: RecipeData) {
        mCoroutineScope.launch(Dispatchers.IO){
            mRecipeRepository.saveRecipeDataToDB(recipeData)
        }
        updateCollectRecipeLiveData(recipeData)
    }

    private fun updateRecommendRecipeLiveData(recipeData: RecipeData) {
        mRecommendRecipeListLiveData.value?.let {
            for(recipe in it){
                if(recipe.id == recipeData.id){
                    recipe.copy(recipeData)
                    break
                }
            }
        }
    }

    private fun updateCollectRecipeLiveData(recipeData: RecipeData){
        Log.d(TAG,"updateCollectRecipeLiveData")
        var collectedRecipes =  mCollectedRecipesLiveData.value
        collectedRecipes?.let {
            if(recipeData.isCollected) {
                it.add(recipeData)
            }else{
                it.remove(recipeData)
            }
        }
        mCollectedRecipesLiveData.postValue(collectedRecipes)
    }

    fun getCollectedRecipesLiveData() :  MutableLiveData<MutableList<RecipeData>> {
        loadCollectedRecipesFromDB()
        return mCollectedRecipesLiveData
    }

    private fun loadCollectedRecipesFromDB() {
        mCoroutineScope.launch(Dispatchers.IO) {
            var recipeDataList = mRecipeRepository.loadCollectedRecipesFromDB()
            try {
                Log.d(TAG,"loadCollectedRecipesFromDB | recipeDataList $recipeDataList")
                mCollectedRecipesLiveData.postValue(recipeDataList)
            }catch (e : Exception) {
                Log.d(TAG,"loadCollectedRecipesFromDB | exception ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }
}