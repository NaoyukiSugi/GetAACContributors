package com.example.getaaccontributors.feature.detail.profile.presenter

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleObserver
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.model.UserList

class DetailProfilePresenter(private val viewProxy: DetailProfileContract.ViewProxy) :
    DetailProfileContract.Presenter,
    LifecycleObserver {

    @VisibleForTesting
    override fun setUser(user: UserList.User) {
        viewProxy.run {
            user.run {
                loadIconImage(avatarUrl)
                setUserName(login)
                setRealName(name)
                setLocation(location)
                setCompany(company)
                setBlog(blog)
                setTwitterUserName(twitterUsername)
            }
        }
    }
}
