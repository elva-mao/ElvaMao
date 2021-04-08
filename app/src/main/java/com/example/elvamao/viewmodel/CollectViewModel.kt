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

class CollectViewModel : ViewModel() {
    companion object {
        val TAG = CollectViewModel::class.simpleName
    }
    private var mContext : Context? = null
    private val recipeRepository: RecipeRepository by lazy { RecipeRepository(mContext) }
    private val collectedRecipesLiveData = MutableLiveData<MutableList<RecipeData>>().apply {
        value = mutableListOf()
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    fun setContext(context : Context?) {
        mContext = context
    }

    fun getCollectedRecipesLiveData() :  MutableLiveData<MutableList<RecipeData>> {
        loadCollectedRecipesFromDB()
        return collectedRecipesLiveData
    }

    fun loadMoreRecipesFromServer(){

    }

     private fun loadCollectedRecipesFromDB() {
        coroutineScope.launch(Dispatchers.IO) {
            var recipeDataList = recipeRepository.loadCollectedRecipesFromDB()
            try {
                Log.d(TAG,"loadCollectedRecipesFromDB | recipeDataList $recipeDataList")
                collectedRecipesLiveData.postValue(recipeDataList)
            }catch (e : Exception) {
                Log.d(TAG,"loadCollectedRecipesFromDB | exception ${e.message}")
            }
        }
    }
}