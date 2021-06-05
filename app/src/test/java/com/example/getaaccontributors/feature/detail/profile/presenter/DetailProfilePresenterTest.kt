package com.example.getaaccontributors.feature.detail.profile.presenter

import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.model.UserList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class DetailProfilePresenterTest {

    private lateinit var presenter: DetailProfilePresenter
    private val viewProxy: DetailProfileContract.ViewProxy = mock()

    @BeforeEach
    fun setUp() {
        presenter = DetailProfilePresenter(viewProxy)
    }

    @Test
    fun `setUser should call all setter & loading methods viewProxy`() {
        val avatarUrl = "avatarUrl"
        val userName = "userName"
        val realName = "realName"
        val location = "location"
        val company = "company"
        val blog = "blog"
        val twitterUserName = "twitterUserName"
        val user: UserList.User = mock {
            on { this.avatarUrl } doReturn avatarUrl
            on { this.login } doReturn userName
            on { this.name } doReturn realName
            on { this.location } doReturn location
            on { this.company } doReturn company
            on { this.blog } doReturn blog
            on { this.twitterUsername } doReturn twitterUserName
        }

        presenter.setUser(user)

        verify(viewProxy).loadIconImage(avatarUrl)
        verify(viewProxy).setUserName(userName)
        verify(viewProxy).setRealName(realName)
        verify(viewProxy).setLocation(location)
        verify(viewProxy).setCompany(company)
        verify(viewProxy).setBlog(blog)
        verify(viewProxy).setTwitterUserName(twitterUserName)
    }
}
