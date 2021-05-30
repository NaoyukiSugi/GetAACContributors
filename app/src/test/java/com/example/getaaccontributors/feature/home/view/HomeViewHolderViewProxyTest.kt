package com.example.getaaccontributors.feature.home.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class HomeViewHolderViewProxyTest {

    private lateinit var viewProxy: HomeViewHolder.ViewProxy
    private val requestManager: RequestManager = mock()
    private val iconIv: ImageView = mock()
    private val nameTv: TextView = mock()
    private val contributionsTv: TextView = mock()
    private val view: View = mock {
        on { findViewById<ImageView>(R.id.icon) } doReturn iconIv
        on { findViewById<TextView>(R.id.name) } doReturn nameTv
        on { findViewById<TextView>(R.id.contributions) } doReturn contributionsTv
    }

    @BeforeEach
    fun setUp() {
        viewProxy = HomeViewHolder.ViewProxy(view, requestManager)
    }

    @Test
    fun `loadIconImage should load image into iconIv`() {
        val imageUrl = "imageUrl"
        val requestBuilder: RequestBuilder<Drawable> = mock()
        doReturn(requestBuilder).whenever(requestManager).load(imageUrl)

        viewProxy.loadIconImage(imageUrl)

        verify(requestBuilder).into(iconIv)
    }

    @Test
    fun `setName should set name into nameTv`() {
        val name = "name"

        viewProxy.setName(name)

        verify(nameTv).text = name
    }

    @Test
    fun `setName should set contributions into contributionsTv`() {
        val contributions = "contributions"

        viewProxy.setContributions(contributions)

        verify(contributionsTv).text = contributions
    }

    @Test
    fun `setOnUserClickListener should set listener into iconIv`() {
        val user: UserList.User = mock()
        val listener: HomeContract.UserClickListener = mock()

        viewProxy.setOnUserClickListener(user, listener)

        val clickListener = argumentCaptor<View.OnClickListener> {
            verify(view).setOnClickListener(capture())
        }.firstValue
        clickListener.onClick(view)

        verify(listener).onUserClick(user)
    }
}
