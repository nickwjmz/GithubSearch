package com.example.githubsearch.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.model.Repo
import com.example.githubsearch.model.RepoResponse

class AllAdapter(val data: List<RepoResponse>) :
        RecyclerView.Adapter<AllAdapter.AllViewHolder>() {

    private val TAG = "AllAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AllViewHolder(LayoutInflater.from(parent.context)
            .inflate(
                R.layout.repo_item_layout,
                parent,
                false)
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AllAdapter.AllViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called")
        holder.onBind(data[position])
    }

    class AllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRepoName: TextView = itemView.findViewById(R.id.tv_repo_name)
        val tvForks: TextView = itemView.findViewById(R.id.tv_forks)
        val tvStars: TextView = itemView.findViewById(R.id.tv_stars)

        fun onBind(item: RepoResponse) {
            tvRepoName.text = item.full_name
            tvForks.text = item.forks_count.toString()
            tvStars.text = item.stargazers_count.toString()
        }
    }
}