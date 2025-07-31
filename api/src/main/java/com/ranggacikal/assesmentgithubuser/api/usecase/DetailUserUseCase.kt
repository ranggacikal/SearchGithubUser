package com.ranggacikal.assesmentgithubuser.api.usecase

import com.ranggacikal.assesmentgithubuser.api.data.UserDetail
import com.ranggacikal.assesmentgithubuser.api.repository.DetailUserRepository
import javax.inject.Inject

class DetailUserUseCase @Inject constructor(
    private val repository: DetailUserRepository
) {
    suspend operator fun invoke(userId: Int): UserDetail {
        return repository.detailUser(userId)
    }
}