package com.example.getaaccontributors.feature.home.repository

import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GetContributorsPagingSourceTest {

    private lateinit var pagingSource: GetContributorsPagingSource
    private val service: GitHubService = mock()
    private val repoId = "123456789"
    private val page = 1

    @BeforeEach
    fun setUp() {
        pagingSource = spy(GetContributorsPagingSource(service, repoId))
    }

    // region fetch
    @Test
    fun `fetch should return UserList when response is successful`() {
        runBlocking {
            val userList: UserList = mock()
            val response: Response<UserList> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn userList
            }
            doReturn(response).whenever(service).getContributors(repoId, page)

            val result = pagingSource.fetch(page)

            assertEquals(userList, result)
        }
    }

    @Test
    fun `fetch should throw HttpException when response is not successful`() {
        runBlocking {
            val response: Response<UserList> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(response).whenever(service).getContributors(repoId, page)

            runCatching {
                pagingSource.fetch(page)
            }.getOrElse {
                assertTrue(it is HttpException)
            }
        }
    }
    // endregion
}
