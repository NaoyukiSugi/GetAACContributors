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

    @GET("users/{user_id}/repos")
    suspend fun getRepos(
        @Path("repo_id") repoId: String,
        @Query("page") page: Int
    ): Response<RepoList>
}
