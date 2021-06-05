package com.example.getaaccontributors.feature.detail.repos.view

import android.view.View
import android.widget.TextView
import com.example.getaaccontributors.R
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DetailReposViewHolderViewProxyTest {

    private lateinit var viewProxy: DetailReposViewHolder.ViewProxy
    private val repoNameTv: TextView = mock()
    private val languageTv: TextView = mock()
    private val updatedDateTv: TextView = mock()
    private val view: View = mock {
        on { findViewById<TextView>(R.id.repo_name) } doReturn repoNameTv
        on { findViewById<TextView>(R.id.language) } doReturn languageTv
        on { findViewById<TextView>(R.id.updated_date) } doReturn updatedDateTv
    }

    @BeforeEach
    fun setUp() {
        viewProxy = DetailReposViewHolder.ViewProxy(view)
    }

    @Test
    fun `setRepoName should set repoName into nameTv`() {
        val repoName = "repoName"

        viewProxy.setRepoName(repoName)

        verify(repoNameTv).text = repoName
    }

    @Test
    fun `setLanguage should set language into languageTv`() {
        val language = "language"

        viewProxy.setLanguage(language)

        verify(languageTv).text = language
    }

    @Test
    fun `setUpdatedDate should set updatedDate into updatedTv`() {
        val updatedDate = "updatedDate"

        viewProxy.setUpdatedDate(updatedDate)

        verify(updatedDateTv).text = updatedDate
    }
}
