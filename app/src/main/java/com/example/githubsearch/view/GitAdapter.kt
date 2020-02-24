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
import com.example.githubsearch.viewmodel.OpenUserDetails

import com.squareup.picasso.Picasso

class GitAdapter(val dataSet: List<Item>,
                 val listener: OpenUserDetails
) : RecyclerView.Adapter<GitAdapter.GitViewHolder>() {

    private val TAG = "GitAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = GitViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.search_item_layout,
                    parent,
                    false))

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.onBind(dataSet[position])
        holder.viewGroup.setOnClickListener {
            listener.openUser(dataSet[position].login)
        }
    }

    class GitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_user_avatar)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_text_username)
        val tvRepoCount: TextView = itemView.findViewById(R.id.tv_text_repo_count)
        val viewGroup: ViewGroup = itemView.findViewById(R.id.user_list_item)

        fun onBind(data: Item) {
            tvUsername.text = data.login
            tvRepoCount.text = data.repos_url
            Picasso.get().load(data.avatar_url).into(ivAvatar)
        }
        /*
        fun onBindRepos(data: SingleUserResponse) {
            tvRepoCount.text = data.public_repos.toString()
        }*/
    }

/*
    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) : Boolean =
                oldItem.login == newItem.login
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }

 */
}
