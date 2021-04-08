package com.example.elvamao.data

import com.google.gson.annotations.SerializedName

//the recipe rest api don't give the ret_code and errMsg, but should design this two field, to show result to users, success or failed
data class RecipeResponse(
    @SerializedName("ret_code") var retCode : Int,
    @SerializedName("err_msg") var errMsg : String,
    @SerializedName("recipes") var recipeList : MutableList<RecipeData>
)
