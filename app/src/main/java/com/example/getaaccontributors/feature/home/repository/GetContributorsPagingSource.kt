package com.example.getaaccontributors.feature.home.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.model.UserList
import retrofit2.HttpException

class GetContributorsPagingSource(
    private val service: GitHubService,
    private val repoId: String
) : PagingSource<Int, UserList.User>() {

    override fun getRefreshKey(state: PagingState<Int, UserList.User>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserList.User> {
        return runCatching {
            val page = params.key ?: 1
            val userList = fetch(page)
            LoadResult.Page(
                data = userList,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (userList.isNotEmpty()) page + 1 else null
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    @VisibleForTesting
    internal suspend fun fetch(page: Int): UserList {
        val response = service.getContributors(repoId, page)
        if (response.isSuccessful) return response.body()!!
        else throw HttpException(response)
    }
}
