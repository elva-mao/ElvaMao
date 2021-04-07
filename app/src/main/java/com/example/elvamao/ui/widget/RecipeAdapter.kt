package com.example.elvamao.ui.widget

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.R
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.ListItemRecipeBinding
import com.example.elvamao.listeners.IUserActionListener
import com.example.elvamao.ui.recommend.RecipeDetailActivity

class RecipeAdapter(private val context : Context) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    companion object{
        val TAG = RecipeAdapter::class.simpleName
    }

    private val mRecipeDatas : MutableList<RecipeData> by lazy { mutableListOf<RecipeData>()}
    private lateinit var mUserActionListener : IUserActionListener

    fun initAdapterData(recipes: MutableList<RecipeData>) {
        mRecipeDatas.apply {
            clear()
            addAll(recipes)
            for(recipe in this){
                recipe.initFakeData()
            }
        }
        notifyDataSetChanged()
        Log.d(TAG, "initAdapterData | recipeDatas : $mRecipeDatas")
    }

    fun setUserInteractionListener(listener: IUserActionListener) {
        mUserActionListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        return  RecipeViewHolder(ListItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false), mUserActionListener)
    }

    override fun getItemCount(): Int = mRecipeDatas.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindData(mRecipeDatas[position])
       // Log.d(TAG, "onBindViewHolder | recipe data : $mRecipeDatas[position]")
    }

    class RecipeViewHolder(private val databinding : ListItemRecipeBinding, private val listener: IUserActionListener) : RecyclerView.ViewHolder(databinding.root), View.OnClickListener {
        init {
            databinding.ivRecipeItemImage.setOnClickListener(this)
            databinding.recipeItemTitle.setOnClickListener(this)
            databinding.btnLike.setOnClickListener(this)
            databinding.btnCollect.setOnClickListener(this)
            databinding.btnShare.setOnClickListener(this)
        }

        private lateinit var recipeData : RecipeData

        fun bindData(recipe : RecipeData) {
            recipeData = recipe
            databinding.recipe = recipe
        }

        override fun onClick(view: View?) {
            //val bundle = bundleOf(KEY_RECIPE_ID to databinding.recipe)
            //Navigation.findNavController(itemView).navigate(R.id.to_detail, bundle)
            when(view?.id) {
                R.id.iv_recipe_item_image, R.id.recipe_item_title -> { RecipeDetailActivity.start(databinding.root.context, recipeData)}
                R.id.btn_like -> handleLikeBtnClicked()
                R.id.btn_collect -> handleCollectBtnClicked()
                R.id.btn_share -> handleShareBtnClicked()
            }
        }

        /**
         * i.update cache data
         * ii.refresh ui
         * iii.async request to server to update the recipe data
         */
        private fun handleLikeBtnClicked() {
            //update cache data
            if(!recipeData.isLiked) {
                recipeData.aggregateLikes++
            } else {
                recipeData.aggregateLikes--
            }
            recipeData.isLiked = !recipeData.isLiked

            //refresh ui
            databinding.recipe = recipeData

            //async request to update the recipe data,
            //as there is no rest api to request, mark it todo
        }

        /**
         * i.update cache data
         * ii.refresh ui
         * iii.async request to server to update the recipe data / or save to db
         */
        private fun handleCollectBtnClicked() {
            if(!recipeData.isCollected) {
                recipeData.collectCount++
            } else {
                recipeData.collectCount--
            }
            recipeData.isCollected = !recipeData.isCollected

            //refresh ui
            databinding.recipe = recipeData

            //async request to update the recipe data,
            //as there is no rest api to request, mark it todo
            //here async save data to db
            listener?.let {
                it.onClickCollect(recipeData)
            }
        }

        private fun handleShareBtnClicked(){
            listener?.let {
                it.onClickShare()
            }
        }
    }

}