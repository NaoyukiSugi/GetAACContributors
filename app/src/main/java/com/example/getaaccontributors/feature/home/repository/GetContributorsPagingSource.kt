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

    override fun getRefreshKey(state: PagingState<Int, UserList.User>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserList.User> {
        TODO("Not yet implemented")
    }

    @VisibleForTesting
    internal suspend fun fetch(page: Int): UserList {
        val response = service.getContributors(repoId, page)
        if (response.isSuccessful) return response.body()!!
        else throw HttpException(response)
    }
}
