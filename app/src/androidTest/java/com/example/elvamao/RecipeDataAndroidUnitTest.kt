package com.example.elvamao

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest

import com.example.elvamao.data.RecipeData
import org.junit.Before

const val TEST_RECIPE_TITLE = "Strawberry Tart"
const val TEST_RECIPE_ID = 128L

// @RunWith is required only if you use a mix of JUnit3 and JUnit4.
@RunWith(AndroidJUnit4::class)
@SmallTest
class RecipeDataAndroidUnitTest {
    private lateinit var recipeData: RecipeData

    @Before
    fun createRecipeData() {
        recipeData = RecipeData(TEST_RECIPE_ID)
    }

    @Test
    fun logHistory_ParcelableWriteRead() {
        val parcel = Parcel.obtain()
        recipeData.apply {
            // Set up the Parcelable object to send and receive.
            title = TEST_RECIPE_TITLE
            // Write the data.
            writeToParcel(parcel, describeContents())
        }

        parcel.setDataPosition(0)

        // Read the data.
        val createdFromParcel: RecipeData = RecipeData.createFromParcel(parcel)
        createdFromParcel.id.also {
            assertEquals(TEST_RECIPE_ID, it)
        }
        createdFromParcel.title.also {
            assertEquals(TEST_RECIPE_TITLE, it)
        }
    }
}
