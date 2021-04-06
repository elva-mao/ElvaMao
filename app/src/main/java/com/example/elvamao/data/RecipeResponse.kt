package com.example.elvamao.data

import com.google.gson.annotations.SerializedName

data class RecipeResponse(@SerializedName("recipes") var recipeList : MutableList<RecipeData>)
