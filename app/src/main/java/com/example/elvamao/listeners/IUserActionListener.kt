package com.example.elvamao.listeners

import com.example.elvamao.data.RecipeData

/**
 * listener that handle user interactions, such as click like/collect/share button
 */
interface IUserActionListener {
    fun onClickLike()
    fun onClickCollect(recipeData: RecipeData)
    fun onClickShare()
    fun onClickTabToRefresh()
}
