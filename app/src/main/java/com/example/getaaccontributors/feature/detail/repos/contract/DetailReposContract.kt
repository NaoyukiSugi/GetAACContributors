package com.example.getaaccontributors.feature.detail.repos.contract

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.flow.Flow

interface DetailReposContract {

    interface Repository {
        fun getRepos(repoId: String): Flow<PagingData<RepoList.Repo>>
    }

    interface ViewHolderViewProxy {
        fun setRepoName(repoName: String)
        fun setLanguage(language: String)
        fun setUpdatedDate(updatedDate: String)
    }

    interface ViewProxy {
        fun initAdapter()
        fun initRecyclerView()
        suspend fun submitData(pagingRepoData: PagingData<RepoList.Repo>)
        fun showRecyclerView()
        fun hideRecyclerView()
        fun showErrorView()
        fun hideErrorView()
        fun showEmptyView()
        fun hideEmptyView()
        fun showLoadingView()
        fun hideLoadingView()
        fun setOnRefreshListener(listener: RefreshListener)
        fun addLoadStateListener(listener: LoadStateListener)
        fun refresh()
        fun showErrorMessage(error: Throwable)
    }

    interface RefreshListener {
        fun onRefresh()
    }

    interface LoadStateListener {
        fun onLoadState(loadState: CombinedLoadStates)
    }

    interface Presenter {
        fun getRepos(userName: String)
    }
}
