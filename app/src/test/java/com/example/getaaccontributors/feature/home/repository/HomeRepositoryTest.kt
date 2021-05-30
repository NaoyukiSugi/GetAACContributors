package com.example.getaaccontributors.feature.home.repository

import androidx.paging.Pager
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.model.UserList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

internal class HomeRepositoryTest {

    private lateinit var repository: HomeRepository
    private val service: GitHubService = mock()

    @BeforeEach
    fun setUp() {
        repository = spy(HomeRepository(service))
    }

    @Test
    fun `getContributors should return flow of Pager`() {
        val pager: Pager<Int, UserList.User> = mock()
        val repoId = "123456789"
        doReturn(pager).whenever(repository).createPager(repoId)

        val result = repository.getContributors(repoId)

        assertEquals(pager.flow, result)
    }
}
