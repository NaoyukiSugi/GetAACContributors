package com.example.getaaccontributors.api.github

import com.example.getaaccontributors.model.RepoList
import com.example.getaaccontributors.model.UserList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("repositories/{repo_id}/contributors")
    suspend fun getContributors(
        @Path("repo_id") repoId: String,
        @Query("page") page: Int
    ): Response<UserList>

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") userName: String,
        @Query("page") page: Int
    ): Response<RepoList>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") userName: String
    ): Response<UserList.User>
}
