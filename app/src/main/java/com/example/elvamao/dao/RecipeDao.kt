package com.example.elvamao.dao

import androidx.room.*
import com.example.elvamao.data.RecipeData

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes() : MutableList<RecipeData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg recipeDatas: RecipeData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeData: RecipeData) : Long

    @Delete()
    fun delete(recipeData: RecipeData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipeData: RecipeData?)
}