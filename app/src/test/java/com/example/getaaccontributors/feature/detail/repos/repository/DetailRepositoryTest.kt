package com.example.getaaccontributors.feature.detail.repos.repository

import androidx.paging.Pager
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.model.RepoList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

internal class DetailRepositoryTest {

    private lateinit var repository: DetailRepository
    private val service: GitHubService = mock()

    @BeforeEach
    fun setUp() {
        repository = spy(DetailRepository(service))
    }

    @Test
    fun `getRepos should return flow of Pager`() {
        val pager: Pager<Int, RepoList.Repo> = mock()
        val userName = "userName"
        doReturn(pager).whenever(repository).createPager(userName)

        val result = repository.getRepos(userName)

        assertEquals(pager.flow, result)
    }
}
