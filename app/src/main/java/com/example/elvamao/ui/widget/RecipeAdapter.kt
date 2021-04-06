package com.example.elvamao.ui.widget

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.R
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.ListItemRecipeBinding
import com.example.elvamao.ui.recommend.RecipeDetailActivity
import com.example.elvamao.ui.recommend.RecipeDetailFragment.Companion.KEY_RECIPE_ID

class RecipeAdapter(private val context : Context) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    companion object{
        val TAG = RecipeAdapter::class.simpleName
    }

    private val recipeDatas : MutableList<RecipeData> by lazy { mutableListOf<RecipeData>()}

    fun initAdapterData(recipes: MutableList<RecipeData>) {
        recipeDatas.apply {
            clear()
            addAll(recipes)
            for(recipe in this){
                recipe.initFakeData()
            }
        }
        notifyDataSetChanged()
        Log.d(TAG, "initAdapterData | recipeDatas : $recipeDatas")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        return  RecipeViewHolder(ListItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = recipeDatas.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindData(recipeDatas[position])
        Log.d(TAG, "onBindViewHolder | recipe : $recipeDatas[position]")
    }

    class RecipeViewHolder(private val databinding : ListItemRecipeBinding) : RecyclerView.ViewHolder(databinding.root), View.OnClickListener {
        init {
            databinding.ivRecipeItemImage.setOnClickListener(this)
            databinding.recipeItemTitle.setOnClickListener(this)
        }

        private lateinit var recipeData : RecipeData

        fun bindData(recipe : RecipeData) {
            recipeData = recipe
            databinding.recipe = recipe
            //Log.d(TAG," reg $recipe")
        }

        override fun onClick(view: View?) {
            //val bundle = bundleOf(KEY_RECIPE_ID to databinding.recipe)
            //Navigation.findNavController(itemView).navigate(R.id.to_detail, bundle)
            when(view?.id) {
                R.id.iv_recipe_item_image, R.id.recipe_item_title -> { RecipeDetailActivity.start(databinding.root.context, recipeData)}
                R.id.btn_like -> updateItem()
            }


        }

        private fun updateItem() {
            //todo update ui and adapter datas , add some animations
        }
    }

}