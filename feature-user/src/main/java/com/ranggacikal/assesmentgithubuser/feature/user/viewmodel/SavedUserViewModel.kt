package com.ranggacikal.assesmentgithubuser.feature.user.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.User
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.UserDatabase
import com.ranggacikal.assesmentgithubuser.feature.user.data.repository.UserRepository
import com.ranggacikal.assesmentgithubuser.feature.user.utils.ResultState
import com.ranggacikal.feature.user.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavedUserViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val userDao = UserDatabase.getInstance(context).userDao()
    private val repository = UserRepository(userDao)

    private val _savedUser = MutableLiveData<ResultState<List<User>>>()
    val savedUser : LiveData<ResultState<List<User>>> = _savedUser

    var users: List<User> = listOf()

    fun deleteUserById(userId: Int) {
        viewModelScope.launch {
            repository.deleteUserById(userId)
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _savedUser.value = ResultState.Loading
            try {
                val users = withContext(Dispatchers.IO) {
                    repository.getAllUsers()
                }
                _savedUser.value = ResultState.Success(users)
            } catch (e: Throwable) {
                e.printStackTrace()
                _savedUser.value =
                    ResultState.Error((e.message ?: R.string.handling_common_error).toString())
            }
        }
    }

}