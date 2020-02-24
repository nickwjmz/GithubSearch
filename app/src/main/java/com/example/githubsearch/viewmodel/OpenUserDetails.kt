package com.example.githubsearch.viewmodel

import com.example.githubsearch.model.SingleUserResponse

interface OpenUserDetails {
    fun openUser(login: String)
}