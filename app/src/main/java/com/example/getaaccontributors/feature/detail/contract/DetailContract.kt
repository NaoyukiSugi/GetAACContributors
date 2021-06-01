package com.example.getaaccontributors.feature.detail.contract

import androidx.paging.PagingData
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.RepoList
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.Flow

interface DetailContract {

    interface Repository {
        fun getRepos(repoId: String): Flow<PagingData<RepoList.Repo>>
    }

    interface ViewHolderViewProxy {
        fun setRepoName(repoName: String)
        fun setLanguage(language: String)
        fun setUpdatedDate(updatedDate: String)
        fun setOnRepoClickListener(repo: RepoList.Repo, listener: RepoClickListener?)
    }

    interface RepoClickListener {
        fun onRepoClick(user: RepoList.Repo)
    }
}
