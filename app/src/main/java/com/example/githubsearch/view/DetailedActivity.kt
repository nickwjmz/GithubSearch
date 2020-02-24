package com.example.githubsearch.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.R
import com.example.githubsearch.model.Repo
import com.example.githubsearch.model.SearchAPI.Companion.create
import com.example.githubsearch.model.SingleUserResponse
import com.example.githubsearch.viewmodel.GitViewModel
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed.*

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

        gitViewModel.getDataUserInfo().observe(
            this,
            Observer<SingleUserResponse>{ t ->
                tv_email.text = t.email.toString()
                tv_location.text = t.location.toString()
                tv_join_date.text = t.created_at
                tv_follower_count.text = t.followers.toString()
                tv_following_count.text = t.following.toString()
                tv_bio_text.text = t.bio.toString()
                Picasso.get().load(t.avatar_url).into(iv_user_avatar)
            })

        gitViewModel.getDataUserRepos()
            .observe(this, Observer<List<Repo>> { t ->
                repo_recycler_view.layoutManager = LinearLayoutManager(this@DetailedActivity)
                repo_recycler_view.adapter = RepoAdapter(t)
            })

        val editTextRepoSearch: TextInputEditText = findViewById(R.id.tiet_repo_search)
        editTextRepoSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!editTextRepoSearch.text.isNullOrEmpty()) {
                    repo_recycler_view.adapter?.notifyDataSetChanged()
                    gitViewModel.searchUsersRepos(create(), tv_username.text.toString(), s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
