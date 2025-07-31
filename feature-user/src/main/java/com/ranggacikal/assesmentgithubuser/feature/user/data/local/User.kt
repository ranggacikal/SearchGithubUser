package com.ranggacikal.assesmentgithubuser.feature.user.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.EMPTY_STRING
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.ZERO

@Entity(tableName = "users")
data class User(
    val login: String = EMPTY_STRING,
    @PrimaryKey val id: Int = ZERO,
    val avatarUrl: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val bio: String = EMPTY_STRING
)
