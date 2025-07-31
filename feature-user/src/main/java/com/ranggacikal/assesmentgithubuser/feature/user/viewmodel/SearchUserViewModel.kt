package com.ranggacikal.assesmentgithubuser.feature.user.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.assesmentgithubuser.api.data.SearchUserResponse
import com.ranggacikal.assesmentgithubuser.api.data.UserDetail
import com.ranggacikal.assesmentgithubuser.api.usecase.DetailUserUseCase
import com.ranggacikal.assesmentgithubuser.api.usecase.SearchUserUseCase
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.User
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.UserDatabase
import com.ranggacikal.assesmentgithubuser.feature.user.data.repository.UserRepository
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.EMPTY_STRING
import com.ranggacikal.assesmentgithubuser.feature.user.utils.ResultState
import com.ranggacikal.feature.user.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val detailUserUseCase: DetailUserUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var currentPage = 1
    private var limitPerPage = 10
    var query: String = EMPTY_STRING
    var isFromEditText = false
    var isLoading = false

    private val _searchUser = MutableLiveData<ResultState<SearchUserResponse>>()
    val searchUser : LiveData<ResultState<SearchUserResponse>> = _searchUser

    private val _detailUser = MutableLiveData<ResultState<UserDetail>>()
    val detailUser: LiveData<ResultState<UserDetail>> = _detailUser

    private val _addUserLocal = MutableLiveData<ResultState<User>>()
    val addUserLocal : LiveData<ResultState<User>> = _addUserLocal

    private val userDao = UserDatabase.getInstance(context).userDao()
    private val repository = UserRepository(userDao)

    fun addUser(user: User) {
        viewModelScope.launch {
            _addUserLocal.value = ResultState.Loading
            try {
                withContext(Dispatchers.IO) {
                    repository.insertUser(user)
                }
                _addUserLocal.value = ResultState.Success(user)
            } catch (e: Exception) {
                e.printStackTrace()
                val errorMessage = e.message ?: context.getString(R.string.handling_common_error)
                _addUserLocal.value = ResultState.Error(errorMessage)
            }
        }
    }

    fun getSearchUser(query: String, page: String) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            _searchUser.value = ResultState.Loading
            try {
                val users = searchUserUseCase(query, limitPerPage.toString(), page)
                _searchUser.value = ResultState.Success(users)
            } catch (e: Throwable) {
                e.printStackTrace()
                _searchUser.value =
                    ResultState.Error((e.message ?: R.string.handling_common_error).toString())
            } finally {
                isLoading = false
            }
        }
    }

    fun getDetailUser(userId: Int) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            _detailUser.value = ResultState.Loading
            try {
                val detailUsers = detailUserUseCase(userId)
                _detailUser.value = ResultState.Success(detailUsers)
            } catch (e: Throwable) {
                e.printStackTrace()
                _detailUser.value =
                    ResultState.Error((e.message ?: R.string.handling_common_error).toString())
            } finally {
                isLoading = false
            }
        }
    }

}