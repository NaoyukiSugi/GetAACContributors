package com.example.getaaccontributors.feature.detail.profile.presenter

import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.model.Future
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DetailProfilePresenterTest {

    private lateinit var presenter: DetailProfilePresenter

    private val viewProxy: DetailProfileContract.ViewProxy = mock()
    private val repository: DetailProfileContract.Repository = mock()

    @BeforeEach
    fun setUp() {
        presenter = DetailProfilePresenter(viewProxy, repository)
    }

    // region getUser
    @Test
    fun `getUser should show ProfileView and set data into views when future is Success`() {
        runBlocking {
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
            doReturn(flowOf(Future.Success(user))).whenever(repository).getUser(userName)

            presenter.getUser(userName)

            verify(viewProxy).hideLoadingView()
            verify(viewProxy).hideErrorView()
            verify(viewProxy).showProfileView()
            verify(viewProxy).loadIconImage(avatarUrl)
            verify(viewProxy).setUserName(userName)
            verify(viewProxy).setRealName(realName)
            verify(viewProxy).setLocation(location)
            verify(viewProxy).setCompany(company)
            verify(viewProxy).setBlog(blog)
            verify(viewProxy).setTwitterUserName(twitterUserName)
        }
    }

    @Test
    fun `getUser should show only ErrorView when result response when future is Error`() {
        runBlocking {
            val userName = "userName"
            doReturn(flowOf(Future.Error(mock()))).whenever(repository).getUser(userName)

            presenter.getUser(userName)

            verify(viewProxy).hideLoadingView()
            verify(viewProxy).hideProfileView()
            verify(viewProxy).showErrorView()
        }
    }

    @Test
    fun `getUser should show only LoadingView when result response when future is Proceeding`() {
        runBlocking {
            val userName = "userName"
            doReturn(flowOf(Future.Proceeding)).whenever(repository).getUser(userName)

            presenter.getUser(userName)

            verify(viewProxy).hideErrorView()
            verify(viewProxy).hideProfileView()
            verify(viewProxy).showLoadingView()
        }
    }
    // endregion
}
