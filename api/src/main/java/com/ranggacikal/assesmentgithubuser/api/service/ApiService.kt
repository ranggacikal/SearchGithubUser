package com.ranggacikal.assesmentgithubuser.api.service

import com.ranggacikal.assesmentgithubuser.api.data.SearchUserResponse
import com.ranggacikal.assesmentgithubuser.api.data.UserDetail
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.DETAIL_USER_API
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.QUERY_NUMBER_OF_PAGE
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.QUERY_PAGE
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.QUERY_Q
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.SEARCH_USER
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.USER_ID_PATH
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(SEARCH_USER)
    suspend fun searchUser(
        @Query(QUERY_Q) query: String,
        @Query(QUERY_NUMBER_OF_PAGE) perPage: String,
        @Query(QUERY_PAGE) page: String
    ): SearchUserResponse

    @GET(DETAIL_USER_API)
    suspend fun detailUser(
        @Path(USER_ID_PATH) userId: Int
    ): UserDetail
}