package com.example.githubsearch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.githubsearch.model.Item
import com.example.githubsearch.model.SearchAPI
import com.example.githubsearch.model.SearchAPI.Companion.create
import com.example.githubsearch.model.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "searchUsers"

class GitViewModel : ViewModel() {

    val dataUserList = MutableLiveData<List<Item>>()

    fun getDataUserList(): LiveData<List<Item>> = dataUserList

    fun searchUsers(service: SearchAPI, query: String) {
        Log.d(TAG, "query: $query")
        service.searchUsers(query).enqueue(
            object : Callback<UsersResponse> {
                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    Log.d(TAG, "Failed to get data")
                }
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    Log.d(TAG, "Got a response: $response")
                    dataUserList.value = response.body()!!.items
                }
            }
        )
    }
}
