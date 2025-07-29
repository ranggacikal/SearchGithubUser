package com.ranggacikal.assesmentgithubuser.api.di

import com.ranggacikal.assesmentgithubuser.api.remote.SearchUserImpl
import com.ranggacikal.assesmentgithubuser.api.repository.SearchUserRepository
import com.ranggacikal.assesmentgithubuser.api.service.ApiService
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideUserRepository(apiService: ApiService): SearchUserRepository =
        SearchUserImpl(apiService)

}