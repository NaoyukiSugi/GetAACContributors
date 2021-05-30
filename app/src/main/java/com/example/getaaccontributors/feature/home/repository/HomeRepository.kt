package com.example.getaaccontributors.feature.home.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val api: GitHubService) : HomeContract.Repository {

    override fun getContributors(repoId: String): Flow<PagingData<UserList.User>> =
        createPager(repoId).flow

    @VisibleForTesting
    internal fun createPager(repoId: String) = Pager(
        config = PagingConfig(
            pageSize = GET_CONTRIBUTORS_SIZE_PER_PAGE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { GetContributorsPagingSource(api, repoId) }
    )

    companion object {
        private const val GET_CONTRIBUTORS_SIZE_PER_PAGE = 30
    }
}
