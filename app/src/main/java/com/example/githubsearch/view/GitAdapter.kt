package com.example.githubsearch.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.model.Item
import com.example.githubsearch.model.UsersResponse
import com.squareup.picasso.Picasso

class GitAdapter(val dataSet: List<Item>) :
    RecyclerView.Adapter<GitAdapter.GitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : GitViewHolder =
        GitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.search_item_layout,
                    parent,
                    false)
        )

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    class GitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_user_avatar)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_text_username)
        val tvRepoCount: TextView = itemView.findViewById(R.id.tv_text_repo_count)

        fun onBind(data : Item){
            tvUsername.text = data.login
            Picasso.get().load(data.avatar_url).into(ivAvatar)
            Log.d("Username", data.login)
        }
    }
}