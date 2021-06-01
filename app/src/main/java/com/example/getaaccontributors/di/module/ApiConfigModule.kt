package com.example.getaaccontributors.di.module

import com.example.getaaccontributors.api.config.Environment
import com.example.getaaccontributors.api.config.ServerConfig
import com.example.getaaccontributors.api.github.GitHubServerConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
class ApiConfigModule {

    @Provides
    @Reusable
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Reusable
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Reusable
    fun provideServerConfig(): ServerConfig {
        return GitHubServerConfig()
    }

    @Provides
    @Reusable
    fun provideEnvironment(serverConfig: ServerConfig): Environment {
        return Environment(serverConfig)
    }
}
