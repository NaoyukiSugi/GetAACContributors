package com.example.getaaccontributors.feature.home.view

import android.view.View
import android.widget.Button
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList

class HomeViewProxy(private val fragment: Fragment) : HomeContract.ViewProxy {

    @VisibleForTesting
    internal lateinit var homeAdapter: HomeAdapter

    private val recyclerView: RecyclerView?
        get() = fragment.view?.findViewById(R.id.recycler_view)

    private val errorView: View?
        get() = fragment.view?.findViewById(R.id.error_view)

    private val emptyView: View?
        get() = fragment.view?.findViewById(R.id.empty_view)

    private val refreshButton: Button?
        get() = fragment.view?.findViewById(R.id.refresh_button)

    private val swipeRefreshLayout: SwipeRefreshLayout?
        get() = fragment.view?.findViewById(R.id.swipe_refresh_layout)

    private val progressBar: ContentLoadingProgressBar?
        get() = fragment.view?.findViewById(R.id.progress_bar)

    override fun initAdapter(userClickListener: HomeContract.UserClickListener) {
        createHomeAdapter().run {
            homeAdapter = this
            recyclerView?.adapter = this
            homeAdapter.userClickListener = userClickListener
        }
    }

    override fun initRecyclerView() {
        recyclerView?.run { addItemDecoration(createItemDecoration()) }
    }

    override suspend fun submitData(pagingUserData: PagingData<UserList.User>) {
        homeAdapter.submitData(pagingUserData)
    }

    override fun showRecyclerView() {
        recyclerView?.isVisible = true
    }

    override fun hideRecyclerView() {
        recyclerView?.isVisible = false
    }

    override fun showErrorView() {
        errorView?.isVisible = true
    }

    override fun hideErrorView() {
        errorView?.isVisible = false
    }

    override fun showEmptyView() {
        emptyView?.isVisible = true
    }

    override fun hideEmptyView() {
        emptyView?.isVisible = false
    }

    override fun showLoadingView() {
        progressBar?.isVisible = true
    }

    override fun hideLoadingView() {
        progressBar?.isVisible = false
    }

    override fun setOnRefreshListener(listener: HomeContract.RefreshListener) {
        refreshButton?.setOnClickListener { listener.onRefresh() }
        swipeRefreshLayout?.run {
            setOnRefreshListener {
                isRefreshing = false
                listener.onRefresh()
            }
        }
    }

    override fun navigateToDetail(user: UserList.User) {
        TODO("Not yet implemented")
    }

    override fun addLoadStateListener(listener: HomeContract.LoadStateListener) {
        homeAdapter.addLoadStateListener { listener.onLoadState(it) }
    }

    @VisibleForTesting
    internal fun createHomeAdapter() = HomeAdapter()

    @VisibleForTesting
    internal fun createItemDecoration() =
        DividerItemDecoration(fragment.context, DividerItemDecoration.VERTICAL)
            .apply {
                fragment.context?.getDrawable(R.drawable.divider)?.let { setDrawable(it) }
            }
}
