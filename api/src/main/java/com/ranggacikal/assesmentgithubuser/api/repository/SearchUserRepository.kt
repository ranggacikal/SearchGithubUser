package com.ranggacikal.assesmentgithubuser.api.repository

import com.ranggacikal.assesmentgithubuser.api.data.SearchUserResponse

interface SearchUserRepository {
    suspend fun searchUser(query: String, perPage: String, page: String): List<SearchUserResponse>
}