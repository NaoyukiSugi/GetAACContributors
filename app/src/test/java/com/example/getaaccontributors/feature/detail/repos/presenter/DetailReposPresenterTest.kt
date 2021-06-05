package com.example.getaaccontributors.feature.detail.repos.presenter

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

internal class DetailReposPresenterTest {

    private lateinit var presenter: DetailReposPresenter
    private val viewProxy: DetailReposContract.ViewProxy = mock()
    private val repository: DetailReposContract.Repository = mock()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        presenter = spy(DetailReposPresenter(viewProxy, repository))
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region getRepos
    @Test
    fun `getRepos should call initAdapter`() {
        runBlocking {
            val userName = "userName"
            val pagingDataFlow: Flow<PagingData<RepoList.Repo>> = mock()
            doReturn(pagingDataFlow).whenever(repository).getRepos(userName)

            presenter.getRepos(userName)

            verify(viewProxy).initAdapter()
        }
    }

    @Test
    fun `getRepos should call initRecyclerView`() {
        runBlocking {
            val userName = "userName"
            val pagingDataFlow: Flow<PagingData<RepoList.Repo>> = mock()
            doReturn(pagingDataFlow).whenever(repository).getRepos(userName)

            presenter.getRepos(userName)

            verify(viewProxy).initRecyclerView()
        }
    }

    @Test
    fun `getRepos should call setOnRefreshListener`() {
        runBlocking {
            val userName = "userName"
            val pagingDataFlow: Flow<PagingData<RepoList.Repo>> = mock()
            doReturn(pagingDataFlow).whenever(repository).getRepos(userName)

            presenter.getRepos(userName)

            verify(viewProxy).setOnRefreshListener(presenter)
        }
    }

    @Test
    fun `getRepos should call addLoadStateListener`() {
        runBlocking {
            val userName = "userName"
            val pagingDataFlow: Flow<PagingData<RepoList.Repo>> = mock()
            doReturn(pagingDataFlow).whenever(repository).getRepos(userName)

            presenter.getRepos(userName)

            verify(viewProxy).addLoadStateListener(presenter)
        }
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
    // endregion

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
        verify(viewProxy).hideErrorView()
    }

    @Test
    fun `onLoadState should only show errorView when LoadState is Error`() {
        val exception = Exception()
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.Error(exception)
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showErrorView()
        verify(viewProxy).hideLoadingView()
        verify(viewProxy).hideRecyclerView()
        verify(viewProxy).showErrorMessage(exception)
    }
    // endregion
}
