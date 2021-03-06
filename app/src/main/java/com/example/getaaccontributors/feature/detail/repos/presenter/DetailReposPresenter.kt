package com.example.getaaccontributors.feature.detail.repos.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailReposPresenter @Inject constructor(
    private val viewProxy: DetailReposContract.ViewProxy,
    private val repository: DetailReposContract.Repository,
    lifecycleOwner: LifecycleOwner
) : DetailReposContract.Presenter,
    DetailReposContract.RefreshListener,
    DetailReposContract.LoadStateListener,
    LifecycleObserver,
    CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleEventOnStart() {
        viewProxy.run {
            initAdapter()
            initRecyclerView()
            setOnRefreshListener(this@DetailReposPresenter)
            addLoadStateListener(this@DetailReposPresenter)
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
