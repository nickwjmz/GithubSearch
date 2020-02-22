package com.example.githubsearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.model.Item
import com.example.githubsearch.model.SearchAPI.Companion.create
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.viewmodel.GitViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gitViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return GitViewModel() as T
                }
            }
        ).get(GitViewModel::class.java)

        gitViewModel.getDataUserList()
            .observe(this,
                Observer<List<Item>>{ t ->
                    recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
                    recycler_view.adapter = GitAdapter(t!!)
            })

        val editTextSearch: TextInputEditText = findViewById(R.id.tiet_github_search)

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!editTextSearch.text.isNullOrEmpty()) {
                    showLogo(s.isNullOrEmpty())
                    gitViewModel.searchUsers(create(), s.toString())
                } else {
                    showLogo(s.isNullOrEmpty())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

    }

    private fun showLogo(show: Boolean) {
        if (show) {
            iv_github_logo.visibility = View.VISIBLE
            recycler_view.visibility = View.GONE
        } else {
            iv_github_logo.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
        }
    }
}
