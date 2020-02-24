package com.example.githubsearch.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAPI {

    // 1896084fb4464374a0693ccaa94059b11814cc25
    // Get users ordered by ID
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UsersResponse>

    // Get single users public info
    @GET("users/{user}")
    fun showUser(
        @Path("user") user: String
    ): Call<SingleUserResponse>

    // Search a users public Repos
    // https://api.github.com/search/repositories?q=g+user:chenwa
    @GET("search/repositories?+user:{username}")
    fun searchUserRepos(
        @Query("q") query: String,
        @Path("username") username: String
    ): Call<UserRepoResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        fun create(): SearchAPI {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SearchAPI::class.java)
        }
    }
}