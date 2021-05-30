package com.example.getaaccontributors.feature.home.view

import android.view.View
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class HomeViewHolderTest {

    private lateinit var viewHolder: HomeViewHolder
    private val view: View = mock()
    private val viewProxy: HomeContract.ViewHolderViewProxy = mock()
    private val user: UserList.User = mock()
    private val avatarUrl = "avatarUrl"
    private val login = "login"
    private val contributions = 1
    private val listener: HomeContract.UserClickListener = mock()


    @BeforeEach
    fun setUp() {
        doReturn(avatarUrl).whenever(user).avatarUrl
        doReturn(login).whenever(user).login
        doReturn(contributions).whenever(user).contributions
        viewHolder = HomeViewHolder(view, viewProxy)
    }

    // region bind
    @Test
    fun `bind should call loadIconImage`() {
        viewHolder.bind(user, listener)

        verify(viewProxy).loadIconImage(user.avatarUrl)
    }

    @Test
    fun `bind should call setName`() {
        viewHolder.bind(user, listener)

        verify(viewProxy).setName(user.login)
    }

    @Test
    fun `bind should call setContributions`() {
        viewHolder.bind(user, listener)

        verify(viewProxy).setContributions(user.contributions.toString())
    }

    @Test
    fun `bind should call setOnUserClickListener`() {
        viewHolder.bind(user, listener)

        verify(viewProxy).setOnUserClickListener(user, listener)
    }
    // endregion
}
