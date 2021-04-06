package com.example.elvamao

import com.example.elvamao.data.RecipeData
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * RecipeData unit test
 */
class RecipeDataTest {
    private lateinit var recipeData: RecipeData

    @Before
    fun setUp() {
        recipeData = RecipeData(1).apply {
            title = "Strawberry Tart"
            imageUrl = "https://spoonacular.com/recipeImages/631762-556x370.jpg"
            aggregateLikes = 8
            collectCount = 5
            shareCount = -1
            readyInMinutes = 30
        }
    }

    @Test fun test_default_values() {
        val defaultRecipeData = RecipeData(2)
        assertEquals(0, defaultRecipeData.aggregateLikes)
        assertEquals("", defaultRecipeData.imageUrl)
    }

    @Test fun test_image_url_not_empty() {
        assertNotNull(recipeData.imageUrl)
        assertNotEquals("", recipeData.imageUrl)
    }

    @Test fun test_title_has_value() {
        assertNotEquals("", recipeData.title)
    }

    @Test fun test_share_count_invalid_value() {
        assertTrue(recipeData.shareCount < 0)
    }

    @Test fun test_aggregate_likes_valid_value() {
        assertTrue(recipeData.aggregateLikes >= 0)
    }
}