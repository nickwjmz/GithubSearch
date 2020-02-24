package com.example.githubsearch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.githubsearch.model.*
import com.example.githubsearch.model.SearchAPI.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "searchUsers"

class GitViewModel : ViewModel() {

    val dataUserList = MutableLiveData<List<Item>>()
    fun getDataUserList(): LiveData<List<Item>> = dataUserList

    fun getEmptyList(): Array<Any> {
        val emptyArray: Array<Any> = emptyArray<Any>()
        return emptyArray
    }

    val dataUserInfo = MutableLiveData<SingleUserResponse>()
    fun getDataUserInfo(): LiveData<SingleUserResponse> = dataUserInfo

    val dataUserRepos = MutableLiveData<List<Repo>>()
    fun getDataUserRepos(): LiveData<List<Repo>> = dataUserRepos

    // Populates Recyclerview with List of users matching query
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

    fun getUser(service: SearchAPI, login: String) {
        Log.d(TAG, "login: $login")
        service.showUser(login).enqueue(
            object: Callback<SingleUserResponse> {
                override fun onFailure(call: Call<SingleUserResponse>, t: Throwable) {
                    t.printStackTrace()
                }
                override fun onResponse(
                    call: Call<SingleUserResponse>,
                    response: Response<SingleUserResponse>
                ) {
                    Log.e(TAG, "Got a response: $response")
                    dataUserInfo.value = response.body()
                }
            }
        )
    }

    fun searchUsersRepos(service: SearchAPI, login: String, query: String) {
        Log.d(TAG, "login: $login, query: $query")
        service.searchUserRepos(query,login).enqueue(
            object : Callback<UserRepoResponse> {
                override fun onFailure(call: Call<UserRepoResponse>, t: Throwable) {
                    Log.d(TAG, "Failed to get RepoResponse")
                    t.printStackTrace()
                }
                override fun onResponse(
                    call: Call<UserRepoResponse>,
                    response: Response<UserRepoResponse>
                ) {
                    Log.d(TAG, "Got a RepoResponse: $response")
                    dataUserRepos.value = response.body()?.items
                }
            }
        )
    }

}
