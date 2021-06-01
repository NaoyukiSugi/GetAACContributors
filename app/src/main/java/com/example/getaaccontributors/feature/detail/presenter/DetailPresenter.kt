package com.example.getaaccontributors.feature.detail.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.getaaccontributors.feature.detail.contract.DetailContract
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailPresenter(
    private val viewProxy: DetailContract.ViewProxy,
    private val repository: DetailContract.Repository,
    lifecycleOwner: LifecycleOwner
) : DetailContract.Presenter,
    DetailContract.RepoClickListener,
    DetailContract.RefreshListener,
    DetailContract.LoadStateListener,
    LifecycleObserver,
    CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleEventOnStart() {
        viewProxy.run {
            initAdapter(this@DetailPresenter)
            initRecyclerView()
            setOnRefreshListener(this@DetailPresenter)
            addLoadStateListener(this@DetailPresenter)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()


    override fun getRepos(userName: String) {
        launch {
            repository.getRepos(userName).collect {
                viewProxy.submitData(it)
            }
        }
    }

    override fun onRepoClick(repo: RepoList.Repo) {
        TODO("Not yet implemented")
    }

    override fun onRefresh() {
        viewProxy.refresh()
    }

    override fun onLoadState(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.NotLoading -> {
                viewProxy.run {
                    showRecyclerView()
                    hideEmptyView()
                    hideErrorView()
                    hideLoadingView()
                }
            }
            LoadState.Loading -> {
                viewProxy.run {
                    showLoadingView()
                    hideRecyclerView()
                    hideEmptyView()
                    hideErrorView()
                }
            }
            is LoadState.Error -> {
                viewProxy.run {
                    showErrorView()
                    hideLoadingView()
                    hideRecyclerView()
                    hideEmptyView()
                }
            }
        }
    }
}
