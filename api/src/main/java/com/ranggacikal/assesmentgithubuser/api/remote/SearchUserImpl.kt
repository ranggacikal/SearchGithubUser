package com.ranggacikal.assesmentgithubuser.api.remote

import com.ranggacikal.assesmentgithubuser.api.data.SearchUserResponse
import com.ranggacikal.assesmentgithubuser.api.repository.SearchUserRepository
import com.ranggacikal.assesmentgithubuser.api.service.ApiService
import javax.inject.Inject

class SearchUserImpl @Inject constructor(
    private val apiService: ApiService
) : SearchUserRepository {
    override suspend fun searchUser(
        query: String,
        perPage: String,
        page: String
    ): List<SearchUserResponse> {
        return apiService.searchUser(query, perPage, page)
    }
}