package com.example.getaaccontributors.feature.detail.repos.view

import android.view.View
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.model.RepoList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DetailReposViewHolderTest {

    private lateinit var viewHolder: DetailReposViewHolder
    private val view: View = mock()
    private val viewProxy: DetailReposContract.ViewHolderViewProxy = mock()
    private val repo: RepoList.Repo = mock()
    private val listener: DetailReposContract.RepoClickListener = mock()

    @BeforeEach
    fun setUp() {
        viewHolder = DetailReposViewHolder(view, viewProxy)
    }

    @Test
    fun `bind should set repoName`() {
        val name = "name"
        doReturn(name).whenever(repo).name

        viewHolder.bind(repo, listener)

        verify(viewProxy).setRepoName(name)
    }

    // region setLanguage
    @Test
    fun `bind should set language when repo's language is not null`() {
        val language = "language"
        doReturn(language).whenever(repo).language

        viewHolder.bind(repo, listener)

        verify(viewProxy).setLanguage(language)
    }

    @Test
    fun `bind should call setLanguage when repo's language is null`() {
        viewHolder.bind(repo, listener)

        verify(viewProxy).setLanguage(DEFAULT_LANGUAGE_TEXT)
    }
    // endregion

    @Test
    fun `bind should set updatedDate`() {
        val updatedAt = "updatedAt"
        doReturn(updatedAt).whenever(repo).updatedAt

        viewHolder.bind(repo, listener)

        verify(viewProxy).setUpdatedDate(updatedAt)
    }

    @Test
    fun `bind should set RepoClickListener`() {
        viewHolder.bind(repo, listener)

        verify(viewProxy).setOnRepoClickListener(repo, listener)
    }

    companion object {
        private const val DEFAULT_LANGUAGE_TEXT = ""
    }
}
