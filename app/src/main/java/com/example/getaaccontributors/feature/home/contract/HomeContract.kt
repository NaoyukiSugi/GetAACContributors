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

    interface ViewProxy {
        fun initAdapter(userClickListener: UserClickListener)
        fun initRecyclerView()
        suspend fun submitData(pagingUserData: PagingData<UserList.User>)
        fun showRecyclerView()
        fun hideRecyclerView()
        fun showErrorView()
        fun hideErrorView()
        fun showEmptyView()
        fun hideEmptyView()
        fun showLoadingView()
        fun hideLoadingView()
        fun setOnRefreshListener(listener: RefreshListener)
        fun navigateToDetail(user: UserList.User)
    }

    interface RefreshListener {
        fun onRefresh()
    }
}
