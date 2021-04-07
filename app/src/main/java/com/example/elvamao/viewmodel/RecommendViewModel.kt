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

class RecommendViewModel: ViewModel() {
    companion object {
        val TAG = RecommendViewModel::class.simpleName
    }
    private var mContext : Context? = null
    private val mRecipeRepository: RecipeRepository by lazy { RecipeRepository(mContext) }
    /**
     * live data stores recipe data List
     */
    private var mRecipeListLiveData : MutableLiveData<MutableList<RecipeData>> = MutableLiveData<MutableList<RecipeData>>()

    private var mViewModelJob = Job()
    private val mCoroutineScope = CoroutineScope( mViewModelJob + Dispatchers.Main )

    fun setContext(context : Context?) {
        mContext = context
    }

    fun getRecipeListLiveData(): MutableLiveData<MutableList<RecipeData>>{
        loadRecipeDataList()
        return mRecipeListLiveData
    }

    private fun loadRecipeDataList() {
        mCoroutineScope.launch(Dispatchers.IO){
            var recipeDataList = mRecipeRepository.fetchRecipeDataList()
            try {
                Log.d(TAG,"getRecipeListLiveData | recipeDataList $recipeDataList")
                mRecipeListLiveData.postValue(recipeDataList)
            }catch (e : Exception) {
                Log.d(TAG,"getRecipeListLiveData | exception ${e.message}")
            }
        }
    }

    fun refreshData() {
        loadRecipeDataList()
    }

    fun saveRecipeDataToDB(recipeData: RecipeData) {
        mCoroutineScope.launch(Dispatchers.IO){
            mRecipeRepository.saveRecipeDataToDB(recipeData)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }

}