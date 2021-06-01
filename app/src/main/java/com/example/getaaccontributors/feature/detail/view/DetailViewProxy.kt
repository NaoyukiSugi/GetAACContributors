package com.example.getaaccontributors.feature.detail.view

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
import com.example.getaaccontributors.feature.detail.contract.DetailContract
import com.example.getaaccontributors.model.RepoList
import javax.inject.Inject

class DetailViewProxy @Inject constructor(
    private val fragment: Fragment
) : DetailContract.ViewProxy {

    @VisibleForTesting
    internal lateinit var detailAdapter: DetailAdapter

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


    override fun initAdapter(repoClickListener: DetailContract.RepoClickListener) {
        createDetailAdapter().run {
            detailAdapter = this
            recyclerView?.adapter = this
            detailAdapter.repoClickListener = repoClickListener
        }
    }

    override fun initRecyclerView() {
        recyclerView?.run { addItemDecoration(createItemDecoration()) }
    }

    override suspend fun submitData(pagingRepoData: PagingData<RepoList.Repo>) {
        detailAdapter.submitData(pagingRepoData)
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

    override fun setOnRefreshListener(listener: DetailContract.RefreshListener) {
        refreshButton?.setOnClickListener { listener.onRefresh() }
        swipeRefreshLayout?.run {
            setOnRefreshListener {
                isRefreshing = false
                listener.onRefresh()
            }
        }
    }

    override fun addLoadStateListener(listener: DetailContract.LoadStateListener) {
        detailAdapter.addLoadStateListener { listener.onLoadState(it) }
    }

    override fun refresh() {
        detailAdapter.refresh()
    }

    @VisibleForTesting
    internal fun createDetailAdapter() = DetailAdapter()

    @VisibleForTesting
    internal fun createItemDecoration() =
        DividerItemDecoration(fragment.context, DividerItemDecoration.VERTICAL)
            .apply {
                fragment.context?.getDrawable(R.drawable.divider)?.let { setDrawable(it) }
            }
}
