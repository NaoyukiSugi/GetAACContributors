package com.example.getaaccontributors.feature.detail.repos.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.model.RepoList

class DetailReposViewHolder(
    private val view: View,
    private val viewProxy: DetailReposContract.ViewHolderViewProxy = ViewProxy(view)
) : RecyclerView.ViewHolder(view) {

    fun bind(repo: RepoList.Repo, listener: DetailReposContract.RepoClickListener?) {
        viewProxy.run {
            setRepoName(repo.name)
            setLanguage(repo.language ?: DEFAULT_LANGUAGE_TEXT)
            setUpdatedDate(repo.updatedAt)
            setOnRepoClickListener(repo, listener)
        }
    }

    internal class ViewProxy(private val view: View) : DetailReposContract.ViewHolderViewProxy {

        private val repoNameTv: TextView
            get() = view.findViewById(R.id.repo_name)

        private val languageTv: TextView
            get() = view.findViewById(R.id.language)

        private val updatedDateTv: TextView
            get() = view.findViewById(R.id.updated_date)

        override fun setRepoName(repoName: String) {
            repoNameTv.text = repoName
        }

        override fun setLanguage(language: String) {
            languageTv.text = language
        }

        override fun setUpdatedDate(updatedDate: String) {
            updatedDateTv.text = updatedDate
        }

        override fun setOnRepoClickListener(
            repo: RepoList.Repo,
            listener: DetailReposContract.RepoClickListener?
        ) {
            view.setOnClickListener { listener?.onRepoClick(repo) }
        }
    }

    companion object {
        private const val DEFAULT_LANGUAGE_TEXT = ""
    }
}
