package com.example.elvamao.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.elvamao.data.RecipeData

@Database(entities = [RecipeData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context?): AppDatabase? {
            if(context == null) {
                return null
            }
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "elvaDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}