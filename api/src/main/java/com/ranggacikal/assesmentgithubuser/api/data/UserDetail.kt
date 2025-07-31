package com.ranggacikal.assesmentgithubuser.api.data

import com.ranggacikal.assesmentgithubuser.api.utils.Constants.EMPTY_STRING
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.ZERO

data class UserDetail(
    val login: String = EMPTY_STRING,
    val id: Int = ZERO,
    val avatarUrl: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val bio: String = EMPTY_STRING
)
