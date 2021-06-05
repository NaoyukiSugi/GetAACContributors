package com.example.getaaccontributors.feature.detail.repos.view

import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DetailReposViewProxyTest {

    private lateinit var viewProxy: DetailViewReposProxy
    private val recyclerView: RecyclerView = mock()
    private val errorView: View = mock()
    private val emptyView: View = mock()
    private val progressBar: ContentLoadingProgressBar = mock()
    private val refreshButton: Button = mock()
    private val swipeRefreshLayout: SwipeRefreshLayout = mock()
    private val view: View = mock {
        on { findViewById<RecyclerView>(R.id.recycler_view) } doReturn recyclerView
        on { findViewById<View>(R.id.error_view) } doReturn errorView
        on { findViewById<View>(R.id.empty_view) } doReturn emptyView
        on { findViewById<ContentLoadingProgressBar>(R.id.progress_bar) } doReturn progressBar
        on { findViewById<Button>(R.id.refresh_button) } doReturn refreshButton
        on { findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout) } doReturn swipeRefreshLayout
    }
    private val fragment: Fragment = mock {
        on { view } doReturn view
    }
    private val adapter: DetailReposAdapter = mock()

    @BeforeEach
    fun setUp() {
        viewProxy = spy(DetailViewReposProxy(fragment))
        doReturn(adapter).whenever(viewProxy).createDetailAdapter()
    }

    // region initAdapter
    @Test
    fun `initAdapter should set adapter into recyclerview`() {
        viewProxy.initAdapter()

        verify(recyclerView).adapter = adapter
    }
    // endregion

    @Test
    fun `initRecyclerView should call addItemDecoration`() {
        val itemDecoration: DividerItemDecoration = mock()
        doReturn(itemDecoration).whenever(viewProxy).createItemDecoration()

        viewProxy.initRecyclerView()

        verify(recyclerView).addItemDecoration(itemDecoration)
    }

    @Test
    fun `submitData should call submitData of adapter`() {
        runBlocking {
            val pagingRepoData: PagingData<RepoList.Repo> = mock()
            viewProxy.detailAdapter = adapter

            viewProxy.submitData(pagingRepoData)

            verify(adapter).submitData(pagingRepoData)
        }
    }

    @Test
    fun `showRecyclerView should show recyclerView`() {
        viewProxy.showRecyclerView()

        verify(recyclerView).isVisible = true
    }

    @Test
    fun `hideRecyclerView should hide recyclerView`() {
        viewProxy.hideRecyclerView()

        verify(recyclerView).isVisible = false
    }

    @Test
    fun `showErrorView should show errorView`() {
        viewProxy.showErrorView()

        verify(errorView).isVisible = true
    }

    @Test
    fun `hideErrorView should hide errorView`() {
        viewProxy.hideErrorView()

        verify(errorView).isVisible = false
    }

    @Test
    fun `showEmptyView should show emptyView`() {
        viewProxy.showEmptyView()

        verify(emptyView).isVisible = true
    }

    @Test
    fun `hideEmptyView should hide emptyView`() {
        viewProxy.hideEmptyView()

        verify(emptyView).isVisible = false
    }

    @Test
    fun `showLoadingView should show progressBar`() {
        viewProxy.showLoadingView()

        verify(progressBar).isVisible = true
    }

    @Test
    fun `hideLoadingView should hide progressBar`() {
        viewProxy.hideLoadingView()

        verify(progressBar).isVisible = false
    }

    @Test
    fun `setOnRefreshListener should set listener into refreshButton`() {
        val refreshListener: DetailReposContract.RefreshListener = mock()

        viewProxy.setOnRefreshListener(refreshListener)

        val tapRefreshListener = argumentCaptor<View.OnClickListener> {
            verify(refreshButton).setOnClickListener(capture())
        }.firstValue
        tapRefreshListener.onClick(refreshButton)

        val swipeRefreshListener = argumentCaptor<SwipeRefreshLayout.OnRefreshListener> {
            verify(swipeRefreshLayout).setOnRefreshListener(capture())
        }.firstValue
        swipeRefreshListener.onRefresh()

        verify(refreshListener, times(2)).onRefresh()
        verify(swipeRefreshLayout).isRefreshing = false
    }

    @Test
    fun `addLoadStateListener should set listener into homeAdapter`() {
        val loadStateListener: DetailReposContract.LoadStateListener = mock()
        viewProxy.detailAdapter = adapter

        viewProxy.addLoadStateListener(loadStateListener)

        argumentCaptor<(CombinedLoadStates) -> Unit> {
            verify(adapter).addLoadStateListener(capture())
        }.firstValue
    }

    @Test
    fun `refresh should call refresh`() {
        viewProxy.detailAdapter = adapter

        viewProxy.refresh()

        verify(adapter).refresh()
    }
}
