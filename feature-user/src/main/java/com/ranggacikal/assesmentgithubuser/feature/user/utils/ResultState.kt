package com.ranggacikal.assesmentgithubuser.feature.user.utils

sealed class ResultState<out T>{
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
}