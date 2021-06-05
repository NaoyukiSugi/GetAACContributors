package com.example.getaaccontributors.feature.detail.profile.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.example.getaaccontributors.R
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DetailProfileViewProxyTest {

    lateinit var viewProxy: DetailProfileViewProxy

    private val userIconIV: ImageView = mock()
    private val userNameTv: TextView = mock()
    private val realNameTv: TextView = mock()
    private val locationTv: TextView = mock()
    private val companyTv: TextView = mock()
    private val blogTv: TextView = mock()
    private val twitterUserNameTv: TextView = mock()
    private val view: View = mock {
        on { findViewById<ImageView>(R.id.icon) } doReturn userIconIV
        on { findViewById<TextView>(R.id.user_name) } doReturn userNameTv
        on { findViewById<TextView>(R.id.real_name) } doReturn realNameTv
        on { findViewById<TextView>(R.id.location) } doReturn locationTv
        on { findViewById<TextView>(R.id.company) } doReturn companyTv
        on { findViewById<TextView>(R.id.blog) } doReturn blogTv
        on { findViewById<TextView>(R.id.twitter_user_name) } doReturn twitterUserNameTv
    }
    private val fragment: Fragment = mock {
        on { view } doReturn view
    }
    private val requestManager: RequestManager = mock()

    @BeforeEach
    fun setUp() {
        viewProxy = spy(DetailProfileViewProxy(fragment, requestManager))
    }

    @Test
    fun `setIcon should load image into iconIV`() {
        val imageUrl = "imageUrl"
        val requestBuilder: RequestBuilder<Drawable> = mock()
        doReturn(requestBuilder).whenever(requestManager).load(imageUrl)

        viewProxy.loadIconImage(imageUrl)

        verify(requestBuilder).into(userIconIV)
    }

    @Test
    fun `setUserName should set userName into userNameTv`() {
        val userName = "userName"

        viewProxy.setUserName(userName)

        verify(userNameTv).text = userName
    }

    @Test
    fun `setRealName should set realName into realNameTv`() {
        val realName = "realName"

        viewProxy.setRealName(realName)

        verify(realNameTv).text = realName
    }

    @Test
    fun `setLocation should set location into locationTv`() {
        val location = "location"

        viewProxy.setLocation(location)

        verify(locationTv).text = location
    }

    @Test
    fun `setCompany should set company into companyTv`() {
        val company = "company"

        viewProxy.setCompany(company)

        verify(companyTv).text = company
    }

    @Test
    fun `setBlog should set blog into blogTv`() {
        val blog = "blog"

        viewProxy.setBlog(blog)

        verify(blogTv).text = blog
    }

    @Test
    fun `setTwitterUserName should set blog into twitterUserNameTv`() {
        val twitterUserName = "twitterUserName"

        viewProxy.setTwitterUserName(twitterUserName)

        verify(twitterUserNameTv).text = twitterUserName
    }
}
