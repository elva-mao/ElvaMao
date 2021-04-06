package com.example.elvamao.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.ActivityRecipeDetailBinding

class RecipeDetailFragment : Fragment() {
    companion object{
        val TAG = RecipeDetailFragment::class.simpleName
        val KEY_RECIPE_ID= "recipe_ID"
    }
    private lateinit var databinding: ActivityRecipeDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = ActivityRecipeDetailBinding.inflate(LayoutInflater.from(context), container, false)
        databinding.recipeData = arguments?.get(KEY_RECIPE_ID) as RecipeData?
        return databinding.root
    }

}