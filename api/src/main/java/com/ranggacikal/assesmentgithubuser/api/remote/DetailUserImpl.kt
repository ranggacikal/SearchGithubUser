package com.ranggacikal.assesmentgithubuser.api.remote

import com.ranggacikal.assesmentgithubuser.api.data.UserDetail
import com.ranggacikal.assesmentgithubuser.api.repository.DetailUserRepository
import com.ranggacikal.assesmentgithubuser.api.service.ApiService
import javax.inject.Inject

class DetailUserImpl @Inject constructor(
    private val apiService: ApiService
) : DetailUserRepository {
    override suspend fun detailUser(userId: Int): UserDetail {
        return apiService.detailUser(userId)
    }
}