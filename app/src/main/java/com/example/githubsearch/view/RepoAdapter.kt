package com.example.githubsearch.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.model.Repo

class RepoAdapter(val data: List<Repo>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val TAG = "RepoAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(LayoutInflater.from(parent.context)
            .inflate(
                R.layout.repo_item_layout,
                parent,
                false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called")
        holder.onBind(data[position])
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRepoName: TextView = itemView.findViewById(R.id.tv_repo_name)
        val tvForks: TextView = itemView.findViewById(R.id.tv_forks)
        val tvStars: TextView = itemView.findViewById(R.id.tv_stars)

        fun onBind(item: Repo) {
            tvRepoName.text = item.full_name
            tvForks.text = item.forks_count.toString()
            tvStars.text = item.stargazers_count.toString()
        }
    }

}