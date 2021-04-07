package com.example.elvamao.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao

    companion object {
        var INSTANCE: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase? {
            if (INSTANCE == null){
                synchronized(AppDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "elvaDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}