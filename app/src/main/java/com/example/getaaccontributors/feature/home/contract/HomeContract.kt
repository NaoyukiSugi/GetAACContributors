package com.example.getaaccontributors.feature.home.contract

import androidx.paging.PagingData
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.Flow

interface HomeContract {

    interface Repository {
        fun getContributors(repoId: String): Flow<PagingData<UserList.User>>
    }

    interface ViewHolderViewProxy {
        fun loadIconImage(imageUrl: String)
        fun setName(name: String)
        fun setContributions(contributions: String)
        fun setOnUserClickListener(user: UserList.User, listener: UserClickListener?)
    }

    interface UserClickListener {
        fun onUserClick(user: UserList.User)
    }
}
