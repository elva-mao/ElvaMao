package com.example.elvamao.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.elvamao.data.Ingredients

@Entity(tableName = "recipe")
data class  RecipeEntity(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "like_count") val likeCnt: Long,
    @ColumnInfo(name = "ready_in_minutes") val readyInMinutes: Int,
    @ColumnInfo(name = "html_instructions") val htmlInstructions: String?,
    @ColumnInfo(name = "collect_count") val collectCnt: Long,
    @ColumnInfo(name = "share_count") val shareCnt: Long
//    @TypeConverters(ListConverter)
//    val ingredients: List<Ingredients>
    //later deal with the last two complex fields
)