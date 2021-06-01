package com.example.getaaccontributors.feature.detail.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.getaaccontributors.feature.detail.contract.DetailContract
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse

internal class DetailPresenterTest {

    private lateinit var presenter: DetailPresenter
    private val viewProxy: DetailContract.ViewProxy = mock()
    private val repository: DetailContract.Repository = mock()
    private val lifecycle: Lifecycle = mock()
    private val lifecycleOwner: LifecycleOwner = mock {
        on { lifecycle } doReturn (lifecycle)
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        presenter = spy(DetailPresenter(viewProxy, repository, lifecycleOwner))
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region onLifecycleEventOnStart
    @Test
    fun `onLifecycleEventOnStart should call initAdapter`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).initAdapter(presenter)
    }

    @Test
    fun `onLifecycleEventOnStart should call initRecyclerView`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).initRecyclerView()
    }

    @Test
    fun `onLifecycleEventOnStart should call setOnRefreshListener`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).setOnRefreshListener(presenter)
    }

    @Test
    fun `onLifecycleEventOnStart should call addLoadStateListener`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).addLoadStateListener(presenter)
    }
    // endregion

    @Test
    fun `onLifecycleEventOnDestroy should call cancel`() {
        presenter.onLifecycleEventOnDestroy()

        assertFalse(presenter.isActive)
    }

    @Test
    fun `getRepos should submit pagingData passed from repository`() {
        runBlocking {
            val userName = "userName"
            val pagingDataFlow: Flow<PagingData<RepoList.Repo>> = mock()
            doReturn(pagingDataFlow).whenever(repository).getRepos(userName)

            presenter.getRepos(userName)

            verify(repository).getRepos(userName)
            pagingDataFlow.collect {
                verify(viewProxy).submitData(it)
            }
        }
    }

    @Test
    fun `onRefresh should call refresh`() {
        presenter.onRefresh()

        viewProxy.refresh()
    }

    // region onLoadState
    @Test
    fun `onLoadState should only show recyclerView when LoadState is NotLoading`() {
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.NotLoading(false)
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showRecyclerView()
        verify(viewProxy).hideEmptyView()
        verify(viewProxy).hideErrorView()
        verify(viewProxy).hideLoadingView()
    }

    @Test
    fun `onLoadState should only show loadingView when LoadState is Loading`() {
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.Loading
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showLoadingView()
        verify(viewProxy).hideRecyclerView()
        verify(viewProxy).hideEmptyView()
        verify(viewProxy).hideErrorView()
    }

    @Test
    fun `onLoadState should only show errorView when LoadState is Error`() {
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.Error(mock())
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showErrorView()
        verify(viewProxy).hideLoadingView()
        verify(viewProxy).hideRecyclerView()
        verify(viewProxy).hideEmptyView()
    }
    // endregion
}
