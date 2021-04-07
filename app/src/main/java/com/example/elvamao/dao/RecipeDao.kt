package com.example.elvamao.dao

import androidx.room.*
import com.example.elvamao.data.RecipeData

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes() : MutableList<RecipeData>

    @Insert
    fun insert(vararg recipeDatas: RecipeData)

    @Delete()
    fun delete(recipeData: RecipeData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipeData: RecipeData?)
}