package com.example.elvamao.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elvamao.data.RecipeData

class RecipeDetailViewModel: ViewModel() {
    private var recipeLiveData : MutableLiveData<RecipeData> = MutableLiveData()

    fun setRecipeLiveData(recipeData: RecipeData) {
        recipeLiveData.postValue(recipeData)
    }

    fun getRecipeLiveData() = recipeLiveData
}