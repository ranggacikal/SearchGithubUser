package com.ranggacikal.assesmentgithubuser.api.usecase

import com.ranggacikal.assesmentgithubuser.api.data.SearchUserResponse
import com.ranggacikal.assesmentgithubuser.api.repository.SearchUserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val repository: SearchUserRepository
) {
    suspend operator fun invoke(query: String, perPage: String, page: String): List<SearchUserResponse> {
        return repository.searchUser(query, perPage, page)
    }
}