package com.example.githubsearch.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.R
import com.example.githubsearch.model.Item
import com.example.githubsearch.model.SearchAPI
import com.example.githubsearch.model.SearchAPI.Companion.create
import com.example.githubsearch.viewmodel.GitViewModel
import com.example.githubsearch.viewmodel.OpenUserDetails
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OpenUserDetails {

    private val TAG = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: started.")

        val gitViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return GitViewModel() as T
                }
            }
        ).get(GitViewModel::class.java)

        gitViewModel.getDataUserList()
            .observe(this, Observer<List<Item>>{ t ->
                    recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
                    recycler_view.adapter = GitAdapter(t, this)
            })

        val editTextSearch: TextInputEditText = findViewById(R.id.tiet_github_search)

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!editTextSearch.text.isNullOrEmpty()) {
                    showLogo(s.isNullOrEmpty())
                    recycler_view.adapter?.notifyDataSetChanged()
                    gitViewModel.searchUsers(create(), s.toString())
                } else {
                    showLogo(s.isNullOrEmpty())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    override fun openUser(login: String) {
        Log.d(TAG, "openUser: Opening new activity: $login")
        val intent = Intent(this, DetailedActivity::class.java)
        intent.putExtra("login", login)
        println(login)
        startActivity(intent)
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
