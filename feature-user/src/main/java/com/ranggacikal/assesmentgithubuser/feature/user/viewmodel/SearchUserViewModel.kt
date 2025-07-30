package com.ranggacikal.assesmentgithubuser.feature.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.assesmentgithubuser.api.data.SearchUserResponse
import com.ranggacikal.assesmentgithubuser.api.usecase.SearchUserUseCase
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.EMPTY_STRING
import com.ranggacikal.assesmentgithubuser.feature.user.utils.ResultState
import com.ranggacikal.feature.user.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    var currentPage = 1
    private var limitPerPage = 10
    var query: String = EMPTY_STRING
    var isFromEditText = false
    var isLoading = false

    private val _searchUser = MutableLiveData<ResultState<SearchUserResponse>>()
    val searchUser : LiveData<ResultState<SearchUserResponse>> = _searchUser

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

}