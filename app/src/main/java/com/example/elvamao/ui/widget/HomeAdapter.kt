package com.example.elvamao.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.R
import com.example.elvamao.databinding.ItemViewTextBinding

class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var dataList : MutableList<String> = arrayListOf()

    companion object{
        val TAG = HomeAdapter::class.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val dataBinding = ItemViewTextBinding.inflate(LayoutInflater.from(context), parent,false)
        return HomeViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun initData(list : MutableList<String>) {
        dataList.clear()
        dataList.addAll(list)
    }

    class HomeViewHolder(private val dataBinding : ItemViewTextBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bindData(content:String){
            dataBinding.text = content
        }
    }
}