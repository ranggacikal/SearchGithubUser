package com.ranggacikal.assesmentgithubuser.api.data

import com.google.gson.annotations.SerializedName
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.INCOMPLETE_RESULTS
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.TOTAL_COUNT_RESPONSE

data class SearchUserResponse(
    val totalCount : Int,
    val incompleteResults: Boolean,
    val items: List<UserData>
)
