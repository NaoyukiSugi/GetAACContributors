package com.example.getaaccontributors.feature.home.contract

import androidx.paging.PagingData
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.Flow

interface HomeContract {

    interface Repository {
        fun getContributors(repoId: String): Flow<PagingData<UserList.User>>
    }
}
