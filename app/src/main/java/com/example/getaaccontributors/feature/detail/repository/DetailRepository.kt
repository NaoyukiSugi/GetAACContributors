package com.example.getaaccontributors.feature.detail.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.detail.contract.DetailContract
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val service: GitHubService
) : DetailContract.Repository {

    override fun getRepos(userName: String): Flow<PagingData<RepoList.Repo>> =
        createPager(userName).flow

    @VisibleForTesting
    internal fun createPager(userName: String) = Pager(
        config = PagingConfig(
            pageSize = GET_REPOS_SIZE_PER_PAGE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { GetReposPagingSource(service, userName) }
    )

    companion object {
        private const val GET_REPOS_SIZE_PER_PAGE = 30
    }
}
