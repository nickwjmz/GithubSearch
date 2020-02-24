package com.example.githubsearch.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.R
import com.example.githubsearch.model.Repo
import com.example.githubsearch.model.RepoResponse
import com.example.githubsearch.model.SearchAPI.Companion.create
import com.example.githubsearch.model.SingleUserResponse
import com.example.githubsearch.viewmodel.GitViewModel
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed.*
import java.lang.NullPointerException

class DetailedActivity: AppCompatActivity() {

    private val TAG = "Detailed Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        Log.d(TAG, "onCreate: started. Intent: $intent")
        tv_username.text = intent.getStringExtra("login")

        val gitViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return GitViewModel() as T
                }
            }
        ).get(GitViewModel::class.java)

        gitViewModel.getUser(create(), tv_username.text.toString())
        val tvEmail: TextView = findViewById(R.id.tv_email)
        val tvLocation: TextView = findViewById(R.id.tv_location)
        val tvJoinDate: TextView = findViewById(R.id.tv_join_date)
        val tvFollowerCount: TextView = findViewById(R.id.tv_follower_count)
        val tvFollowing: TextView = findViewById(R.id.tv_following_count)
        val tvBio: TextView = findViewById(R.id.tv_bio_text)
        gitViewModel.getDataUserInfo().observe(
            this,
            Observer<SingleUserResponse>{ t ->
                tvEmail.text = try {
                    t.email.toString()
                } catch (exception: NullPointerException) {
                    "No email"
                }
                tvLocation.text = try {
                    t.location.toString()
                } catch (exception: NullPointerException) {
                    "Location not specified"
                }
                tvJoinDate.text = try {
                    t.created_at
                } catch (exception: NullPointerException) {
                    "No creation date?"
                }
                tvFollowerCount.text = try {
                    t.followers.toString()
                } catch (exception: NullPointerException) {
                    "No followers detected."
                }
                tvFollowing.text = try {
                   t.following.toString()
                } catch (exception: NullPointerException) {
                    "Following no one."
                }
                tvBio.text = try {
                    t.bio.toString()
                } catch (exception: NullPointerException) {
                    "No bio."
                }
                try {
                    Picasso.get().load(t.avatar_url).into(iv_user_avatar)
                } catch (exception: NullPointerException) {
                    Picasso.get().load(R.drawable.github_mark_64px).into(iv_user_avatar)
                }
            })
        gitViewModel.getDataShowRepos()
            .observe(this@DetailedActivity, Observer<List<RepoResponse>> { t ->
                repo_recycler_view.layoutManager = LinearLayoutManager(this@DetailedActivity)
                repo_recycler_view.adapter = AllAdapter(t)
            })
        gitViewModel.showUserRepoList(create(), tv_username.text.toString())
        val editTextRepoSearch: TextInputEditText = findViewById(R.id.tiet_repo_search)


        editTextRepoSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!editTextRepoSearch.text.isNullOrEmpty()) {
                    repo_recycler_view.adapter?.notifyDataSetChanged()
                    gitViewModel.getDataUserRepos().observe(
                        this@DetailedActivity, Observer<List<Repo>> { t ->
                            repo_recycler_view.layoutManager = LinearLayoutManager(this@DetailedActivity)
                            repo_recycler_view.adapter = RepoAdapter(t)
                        }
                    )
                    gitViewModel.searchUsersRepos(create(), s.toString()+"+user:"+tv_username.text.toString())
                } else {
                    gitViewModel.showUserRepoList(create(), tv_username.text.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
