package com.example.getaaccontributors.feature.detail.repos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.detail.repos.contract.DetailContract
import com.example.getaaccontributors.model.RepoList

class DetailAdapter : PagingDataAdapter<RepoList.Repo, DetailViewHolder>(DIFF_CALLBACK) {

    var repoClickListener: DetailContract.RepoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return createDetailViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        getRepo(position)?.run { holder.bind(this, repoClickListener) }
    }

    @VisibleForTesting
    internal fun createDetailViewHolder(parent: ViewGroup): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_repo, parent, false)
        return DetailViewHolder(view)
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
