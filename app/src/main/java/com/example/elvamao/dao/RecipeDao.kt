package com.example.elvamao.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes() : MutableList<RecipeEntity>

    @Insert
    fun insert(vararg recipeEntitys: RecipeEntity)

    @Delete()
    fun delete(recipeEntity: RecipeEntity)
}