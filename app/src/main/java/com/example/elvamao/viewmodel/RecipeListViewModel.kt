package com.example.elvamao.viewmodel

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

class RecipeListViewModel() : ViewModel() {
    companion object {
        val TAG = RecipeListViewModel::class.simpleName
    }
    private val recipeRepository: RecipeRepository by lazy { RecipeRepository() }
    /**
     * live data stores recipe data List
     */
    private var recipeListLiveData : MutableLiveData<MutableList<RecipeData>> = MutableLiveData<MutableList<RecipeData>>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    fun getRecipeListLiveData(): MutableLiveData<MutableList<RecipeData>>{
        loadRecipeDataList()
        return recipeListLiveData
    }

    private fun loadRecipeDataList() {
        coroutineScope.launch {
            var recipeDataList = recipeRepository.fetchRecipeDataList()
            try {
                Log.d(TAG,"getRecipeListLiveData | recipeDataList $recipeDataList")
                recipeListLiveData.postValue(recipeDataList)
            }catch (e : Exception) {
                Log.d(TAG,"getRecipeListLiveData | exception ${e.message}")
            }
        }
    }

    fun refreshData() {
        loadRecipeDataList()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}