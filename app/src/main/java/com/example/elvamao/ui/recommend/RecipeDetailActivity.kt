package com.example.elvamao.ui.recommend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import com.example.elvamao.R
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.ActivityRecipeDetailBinding
import com.example.elvamao.databinding.RichTextItemBinding
import com.example.elvamao.util.Utils


class RecipeDetailActivity : AppCompatActivity() {
    companion object{
        val TAG = RecipeDetailActivity::class.simpleName
        const val KEY_RECIPE_DATA= "recipe_data"

        fun start(context : Context, recipeData : RecipeData) {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(KEY_RECIPE_DATA, recipeData)
            context.startActivity(intent)
        }
    }

    private lateinit var databinding : ActivityRecipeDetailBinding
    private lateinit var recipeData: RecipeData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = ActivityRecipeDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(databinding.root)
        initData()
        initViews()
    }

    @SuppressLint("WrongConstant")
    private fun initViews() {
        setupToolbar()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            databinding.recipeInstructions.justificationMode = JUSTIFICATION_MODE_INTER_WORD
//        }
//        databinding.toolbar.setOnMenuItemClickListener({
//            //Toast.makeText(this, "share this recipe", Toast.LENGTH_LONG).show()
//        })

        //just encounter data trans problem, will find solutions to solve it later(as time limited)
//        dynamicCreateInstructionView()
//        dynamicCreateIngredientsView()
    }


    private fun setupToolbar(){
        databinding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

        databinding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_share -> {
                    Toast.makeText(this, resources?.getString(R.string.share_recipe_to_social_platforms), Toast.LENGTH_LONG).show()
                    //generateShareIntent() todo
                    true
                }
                else -> false
            }
        }

        var isToolbarShown = false
        // scroll change listener begins at Y = 0 when image is fully collapsed
        databinding.recipeDetailScrollview.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                // User scrolled past image to height of toolbar and the title text is
                // underneath the toolbar, so the toolbar should be shown.
                val shouldShowToolbar = scrollY > databinding.toolbar.height
                // The new state of the toolbar differs from the previous state; update
                // appbar and toolbar attributes.
                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar
                    // Use shadow animator to add elevation if toolbar is shown
                    databinding.appbar.isActivated = shouldShowToolbar
                    // Show the recipe name if toolbar is shown
                    databinding.toolbarLayout.isTitleEnabled = shouldShowToolbar
                }
            }
        )

    }

//    private fun dynamicCreateIngredientsView() {
//        val container = databinding.recipeIngredientsContainer
//        recipeData?.let {
//            val ingredientList = recipeData.extendedIngredients
//            for (ingredient in ingredientList) {
//                val amountText = ingredient.amount.toString() + " " + ingredient.unit
//                val nameText = ingredient.name
//                val richContent = Utils.getRichContent(this, amountText, nameText)
//                val itemView = createTextItemView(richContent)
//                container.addView(itemView)
//            }
//        }
//    }

//    private fun dynamicCreateInstructionView() {
//        val container = databinding.recipeIngredientsContainer
//        recipeData?.let {
//            val instructionList = recipeData.instructions
//            var stepNum = 1
//            for (instruction in instructionList) {
//                val leftText = stepNum.toString()
//                val rightText = instruction.name ?: ""
//                stepNum++
//                val richContent = Utils.getRichContent(this, leftText, rightText)
//                val itemView = createTextItemView(richContent)
//                container.addView(itemView)
//            }
//        }
//    }

    private fun createTextItemView(richContent : Spannable) : View{
        val textItemDataBinding = RichTextItemBinding.inflate(LayoutInflater.from(this))
        textItemDataBinding.tvContent.text = richContent
        return textItemDataBinding.root
    }

    private fun initData(){
        intent?.let {
            recipeData = intent.getParcelableExtra(KEY_RECIPE_DATA)!!
            databinding.recipeData = recipeData
        }
    }
}