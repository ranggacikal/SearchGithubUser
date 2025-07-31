package com.ranggacikal.assesmentgithubuser.api.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ranggacikal.assesmentgithubuser.api.remote.DetailUserImpl
import com.ranggacikal.assesmentgithubuser.api.remote.SearchUserImpl
import com.ranggacikal.assesmentgithubuser.api.repository.DetailUserRepository
import com.ranggacikal.assesmentgithubuser.api.repository.SearchUserRepository
import com.ranggacikal.assesmentgithubuser.api.service.ApiService
import com.ranggacikal.assesmentgithubuser.api.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .alwaysReadResponseBody(true)
            .build()

        builder.addInterceptor(chuckerInterceptor)

        return builder.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideUserRepository(apiService: ApiService): SearchUserRepository =
        SearchUserImpl(apiService)

    @Provides
    fun provideDetailUserRepository(apiService: ApiService): DetailUserRepository =
        DetailUserImpl(apiService)

}