package com.example.getaaccontributors.di.module

import com.example.getaaccontributors.api.config.Environment
import com.example.getaaccontributors.api.github.GitHubService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Reusable
    fun provideRetrofit(okHttpClient: OkHttpClient, environment: Environment): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(environment.getBaseUrl())
            .client(okHttpClient)
            .build()

    @Provides
    @Reusable
    fun provideGitHubService(retrofit: Retrofit): GitHubService =
        retrofit.create(GitHubService::class.java)
}
