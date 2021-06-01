package com.example.getaaccontributors.feature.detail.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.model.RepoList
import retrofit2.HttpException

class GetReposPagingSource(
    private val service: GitHubService,
    private val userName: String
) : PagingSource<Int, RepoList.Repo>() {

    override fun getRefreshKey(state: PagingState<Int, RepoList.Repo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoList.Repo> {
        return runCatching {
            val page = params.key ?: 1
            val repoList = fetch(page)
            LoadResult.Page(
                data = repoList,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (repoList.isNotEmpty()) page + 1 else null
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    @VisibleForTesting
    internal suspend fun fetch(page: Int): RepoList {
        val response = service.getRepos(userName, page)
        if (response.isSuccessful) return response.body()!!
        else throw HttpException(response)
    }
}
