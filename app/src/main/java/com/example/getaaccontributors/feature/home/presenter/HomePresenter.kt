package com.example.getaaccontributors.feature.home.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.paging.CombinedLoadStates
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomePresenter(
    private val viewProxy: HomeContract.ViewProxy,
    private val repository: HomeContract.Repository,
    lifecycleOwner: LifecycleOwner
) : HomeContract.Presenter,
    HomeContract.UserClickListener,
    HomeContract.RefreshListener,
    HomeContract.LoadStateListener,
    LifecycleObserver,
    CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleEventOnStart() {
        viewProxy.run {
            initAdapter(this@HomePresenter)
            initRecyclerView()
            setOnRefreshListener(this@HomePresenter)
            addLoadStateListener(this@HomePresenter)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()

    override fun getContributors() {
        launch {
            repository.getContributors(REPO_ID).collect {
                viewProxy.submitData(it)
            }
        }
    }

    override fun onUserClick(user: UserList.User) {
        TODO("Not yet implemented")
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }

    override fun onLoadState(loadState: CombinedLoadStates) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val REPO_ID = "90792131"
    }
}
