package com.example.elvamao.ui.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.R

class RecommendAdapter(private val users: MutableList<String>) : RecyclerView.Adapter<RecommendAdapter.DataViewHolder>() {
        class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(name : String) {
                itemView.apply {
                    findViewById<TextView>(R.id.tv_reply_msg).text = name
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
            DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_text, parent, false))

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
            holder.bind(users[position])
        }

        fun addUsers(users: MutableList<String>) {
            this.users.apply {
                clear()
                addAll(users)
            }

        }
}