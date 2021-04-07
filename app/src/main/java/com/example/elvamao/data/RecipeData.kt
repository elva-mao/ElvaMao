package com.example.elvamao.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlin.random.Random

@Entity(tableName = "recipe")
data class RecipeData(@PrimaryKey @SerializedName("id") var id : Long) : Parcelable{
    @ColumnInfo
    @SerializedName("title")
    var title : String = ""
    @ColumnInfo
    @SerializedName("image")
    var imageUrl : String = ""
    @ColumnInfo
    @SerializedName("aggregateLikes")
    var aggregateLikes : Long = 0L
    @ColumnInfo
    @SerializedName("readyInMinutes")
    var readyInMinutes : Int = 0
    //as the rest point response data don't have collect or share count data, just fake some data
    @ColumnInfo
    var collectCount : Long = 0
    @ColumnInfo
    var shareCount : Long = 0
    @ColumnInfo
    var isLiked : Boolean = false
    @ColumnInfo
    var isCollected : Boolean = false
    // encounter a problem that this field content size is too big which cause data lose when use intent to trans between activitys
    @Ignore
    @SerializedName("extendedIngredients")
    var extendedIngredients: MutableList<Ingredients> = mutableListOf()
    @Ignore
    @SerializedName("analyzedInstructions")
    var instructions: MutableList<Instruction> = mutableListOf()
    @ColumnInfo
    @SerializedName("instructions")
    var htmlInstructions : String = ""


    constructor(parcel: Parcel) : this(parcel.readLong()) {
        title = parcel.readString().toString()
        imageUrl = parcel.readString().toString()
        aggregateLikes = parcel.readLong()
        readyInMinutes = parcel.readInt()
        htmlInstructions = parcel.readString().toString()
    }

    fun initFakeData() {
         collectCount = Random.nextLong(0, aggregateLikes)
         shareCount = Random.nextLong(0, aggregateLikes)
    }


//    companion object{
//        fun fakeMsgList() : MutableList<RecipeData>{
//            val list = arrayListOf<RecipeData>()
//            list.apply {
//                add(RecipeData(1).apply {
//                    title = "Chocolate Nutella Walnut Cake"
//                    imageUrl = "https://spoonacular.com/recipeImages/639114-556x370.jpg"
//                })
//                add(RecipeData(2).apply {
//                    title = "Strawberry Tart"
//                    imageUrl = "https://spoonacular.com/recipeImages/631762-556x370.jpg"
//                })
//                add(RecipeData(3).apply {
//                    title = "Cake with lemon, rosewater and pistachios"
//                    imageUrl = "https://spoonacular.com/recipeImages/636766-556x370.jpg"
//                })
//            }
//            return list
//        }
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeLong(aggregateLikes)
        parcel.writeInt(readyInMinutes)
        parcel.writeString(htmlInstructions)
        parcel.writeLong(collectCount)
        parcel.writeLong(shareCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "RecipeData(id=$id, title='$title', imageUrl='$imageUrl', aggregateLikes=$aggregateLikes, readyInMinutes=$readyInMinutes, collectCount=$collectCount, shareCount=$shareCount, isLiked=$isLiked, isCollected=$isCollected, extendedIngredients=$extendedIngredients, instructions=$instructions, htmlInstructions='$htmlInstructions')"
    }

    companion object CREATOR : Parcelable.Creator<RecipeData> {
        override fun createFromParcel(parcel: Parcel): RecipeData {
            return RecipeData(parcel)
        }

        override fun newArray(size: Int): Array<RecipeData?> {
            return arrayOfNulls(size)
        }
    }

}

data class Ingredients(@SerializedName("id") var id : Long) : Parcelable{
    @SerializedName("name")
    var name : String = ""
    @SerializedName("image")
     var imageRelativePath : String = ""
    @SerializedName("original")
    var original : String = ""
    @SerializedName("amount")
    var amount : Float = 0f
    @SerializedName("unit")
    var unit : String = ""

    constructor(parcel: Parcel) : this(parcel.readLong()) {
        name = parcel.readString().toString()
        imageRelativePath = parcel.readString().toString()
        original = parcel.readString().toString()
        amount = parcel.readFloat()
        unit = parcel.readString().toString()
    }

    override fun toString(): String {
        return "Ingredients(id=$id, name='$name', imageRelativePath='$imageRelativePath', original='$original', amount=$amount, unit='$unit')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(imageRelativePath)
        parcel.writeString(original)
        parcel.writeFloat(amount)
        parcel.writeString(unit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ingredients> {
        override fun createFromParcel(parcel: Parcel): Ingredients {
            return Ingredients(parcel)
        }

        override fun newArray(size: Int): Array<Ingredients?> {
            return arrayOfNulls(size)
        }
    }
}

data class Instruction(@SerializedName("name") var name: String?):Parcelable{
    @SerializedName("steps")
    var steps : MutableList<Step> = mutableListOf()

    constructor(parcel: Parcel) : this(parcel.readString()) {

    }

    override fun toString(): String {
        return "Instruction(name='$name', steps=$steps)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Instruction> {
        override fun createFromParcel(parcel: Parcel): Instruction {
            return Instruction(parcel)
        }

        override fun newArray(size: Int): Array<Instruction?> {
            return arrayOfNulls(size)
        }
    }
}

data class Step(@SerializedName("number") var number : Int):Parcelable{
    @SerializedName("step")
    var step : String = ""
    @SerializedName("ingredients")
    var ingredients : MutableList<Ingredients> = mutableListOf()
    @SerializedName("equipment")
    var equiments : MutableList<Equipment> = mutableListOf()

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        step = parcel.readString().toString()
    }

    override fun toString(): String {
        return "Step(number=$number, step='$step', ingredients=$ingredients, equiments=$equiments)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeString(step)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Step> {
        override fun createFromParcel(parcel: Parcel): Step {
            return Step(parcel)
        }

        override fun newArray(size: Int): Array<Step?> {
            return arrayOfNulls(size)
        }
    }
}

data class Equipment(@SerializedName("id") var id : Int):Parcelable{
    @SerializedName("name")
    var name : String = ""
    @SerializedName("image")
    var image : String = ""

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        name = parcel.readString().toString()
        image = parcel.readString().toString()
    }

    override fun toString(): String {
        return "Equipment(id=$id, name='$name', image='$image')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Equipment> {
        override fun createFromParcel(parcel: Parcel): Equipment {
            return Equipment(parcel)
        }

        override fun newArray(size: Int): Array<Equipment?> {
            return arrayOfNulls(size)
        }
    }
}