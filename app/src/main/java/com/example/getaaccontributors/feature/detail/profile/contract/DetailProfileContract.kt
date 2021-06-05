package com.example.getaaccontributors.feature.detail.profile.contract

import com.example.getaaccontributors.model.Future
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.Flow

interface DetailProfileContract {

    interface Repository {
        suspend fun getUser(userName: String): Flow<Future<UserList.User>>
    }

    interface ViewProxy {
        fun showProfileView()
        fun hideProfileView()
        fun showErrorView()
        fun hideErrorView()
        fun showLoadingView()
        fun hideLoadingView()
        fun loadIconImage(imageUrl: String)
        fun setUserName(userName: String)
        fun setRealName(realName: String)
        fun setLocation(location: String)
        fun setCompany(company: String)
        fun setBlog(blog: String)
        fun setTwitterUserName(twitterUserName: String)
    }

    interface Presenter {
        suspend fun getUser(userName: String)
    }
}
