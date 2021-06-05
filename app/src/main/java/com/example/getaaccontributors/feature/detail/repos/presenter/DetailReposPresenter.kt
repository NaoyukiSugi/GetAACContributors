package com.example.getaaccontributors.feature.detail.repos.presenter

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DetailReposPresenter @Inject constructor(
    private val viewProxy: DetailReposContract.ViewProxy,
    private val repository: DetailReposContract.Repository
) : DetailReposContract.Presenter,
    DetailReposContract.RefreshListener,
    DetailReposContract.LoadStateListener,
    CoroutineScope by MainScope() {

    override suspend fun getRepos(userName: String) {
        viewProxy.run {
            initAdapter()
            initRecyclerView()
            setOnRefreshListener(this@DetailReposPresenter)
            addLoadStateListener(this@DetailReposPresenter)
        }

        repository.getRepos(userName).collect {
            viewProxy.submitData(it)
        }
    }

    override fun onRefresh() {
        viewProxy.refresh()
    }

    override fun onLoadState(loadState: CombinedLoadStates) {
        when (val refresh = loadState.refresh) {
            is LoadState.NotLoading -> {
                viewProxy.run {
                    showRecyclerView()
                    hideErrorView()
                    hideLoadingView()
                }
            }
            LoadState.Loading -> {
                viewProxy.run {
                    showLoadingView()
                    hideRecyclerView()
                    hideErrorView()
                }
            }
            is LoadState.Error -> {
                viewProxy.run {
                    showErrorView()
                    hideLoadingView()
                    hideRecyclerView()
                    showErrorMessage(refresh.error)
                }
            }
        }
    }
}
