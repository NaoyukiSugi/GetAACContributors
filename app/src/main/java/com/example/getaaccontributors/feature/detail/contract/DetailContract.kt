package com.example.getaaccontributors.feature.detail.contract

import androidx.paging.PagingData
import com.example.getaaccontributors.model.RepoList
import kotlinx.coroutines.flow.Flow

interface DetailContract {

    interface Repository {
        fun getRepos(repoId: String): Flow<PagingData<RepoList.Repo>>
    }
}
