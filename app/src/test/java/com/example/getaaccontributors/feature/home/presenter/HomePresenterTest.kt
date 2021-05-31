package com.example.getaaccontributors.feature.home.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.getaaccontributors.feature.home.contract.HomeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import kotlin.test.assertFalse

internal class HomePresenterTest {

    private lateinit var presenter: HomePresenter
    private val viewProxy: HomeContract.ViewProxy = mock()
    private val repository: HomeContract.Repository = mock()
    private val lifecycle: Lifecycle = mock()
    private val lifecycleOwner: LifecycleOwner = mock {
        on { lifecycle } doReturn (lifecycle)
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        presenter = spy(HomePresenter(viewProxy, repository, lifecycleOwner))
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

}