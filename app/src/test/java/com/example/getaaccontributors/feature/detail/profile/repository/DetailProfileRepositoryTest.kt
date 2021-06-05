package com.example.getaaccontributors.feature.detail.profile.repository

import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.model.Future
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

internal class DetailProfileRepositoryTest {

    private lateinit var repository: DetailProfileRepository
    private val service: GitHubService = mock()
    private val userName = "userName"

    @BeforeEach
    internal fun setUp() {
        repository = DetailProfileRepository(service)
    }

    // region getUser
    @Test
    fun `getUser should emit Success when response is successful`() {
        runBlocking {
            val user: UserList.User = mock()
            val response: Response<UserList.User> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn user
            }
            doReturn(response).whenever(service).getUser(userName)

            val result = repository.getUser(userName)

            result.collect {
                it.transform { it ->
                    assertEquals(user, it)
                }
            }
        }
    }

    @Test
    fun `getUser should emit Error of HttpException when response is not successful`() {
        runBlocking {
            val response: Response<UserList.User> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(response).whenever(service).getUser(userName)

            val result = repository.getUser(userName)

            result.catch { error ->
                assertTrue(error is HttpException)
            }
        }
    }

    @Test
    fun `getUser should emit Error of NullPointerException when response is null`() {
        runBlocking {
            val result = repository.getUser(userName)

            result.catch { error ->
                assertTrue(error is NullPointerException)
            }
        }
    }

    @Test
    fun `getUser should emit Proceeding when starting`() {
        runBlocking {
            val result = repository.getUser(userName).first()

            assertTrue(result is Future.Proceeding)
        }
    }
    // endregion
}
