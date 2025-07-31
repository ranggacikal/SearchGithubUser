package com.ranggacikal.assesmentgithubuser.api.repository

import com.ranggacikal.assesmentgithubuser.api.data.UserDetail

interface DetailUserRepository {
    suspend fun detailUser(userId: Int): UserDetail
}