package com.ranggacikal.assesmentgithubuser.feature.user.data.repository

import com.ranggacikal.assesmentgithubuser.feature.user.data.local.User
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun deleteUserById(userId: Int) = userDao.deleteUserById(userId)
    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()
}