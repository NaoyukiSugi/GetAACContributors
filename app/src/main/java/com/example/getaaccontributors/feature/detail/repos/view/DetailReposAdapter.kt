package com.example.getaaccontributors.feature.detail.repos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.model.RepoList

class DetailReposAdapter : PagingDataAdapter<RepoList.Repo, DetailReposViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReposViewHolder {
        return createDetailViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DetailReposViewHolder, position: Int) {
        getRepo(position)?.run { holder.bind(this) }
    }

    @VisibleForTesting
    internal fun createDetailViewHolder(parent: ViewGroup): DetailReposViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_repo, parent, false)
        return DetailReposViewHolder(view)
    }

    @VisibleForTesting
    internal fun getRepo(position: Int) = getItem(position)

    companion object {
        @VisibleForTesting
        internal val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepoList.Repo>() {

            override fun areItemsTheSame(
                oldItem: RepoList.Repo,
                newItem: RepoList.Repo
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepoList.Repo,
                newItem: RepoList.Repo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
