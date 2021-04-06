package com.example.elvamao.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectViewModel : ViewModel() {

    private val collectListLiveData = MutableLiveData<MutableList<String>>().apply {
        value = arrayListOf("haha","jhdjahd","hdfjahd")
    }

    fun getCollectListLiveData() :  MutableLiveData<MutableList<String>> = collectListLiveData
}