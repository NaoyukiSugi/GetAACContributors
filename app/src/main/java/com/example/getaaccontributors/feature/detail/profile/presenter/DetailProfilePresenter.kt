package com.example.getaaccontributors.feature.detail.profile.presenter

import androidx.lifecycle.LifecycleObserver
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.model.Future
import kotlinx.coroutines.flow.collect

class DetailProfilePresenter(
    private val viewProxy: DetailProfileContract.ViewProxy,
    private val repository: DetailProfileContract.Repository
) : DetailProfileContract.Presenter,
    LifecycleObserver {

    override suspend fun getUser(userName: String) {

        repository.getUser(userName).collect {
            when (it) {
                is Future.Success -> {
                    viewProxy.run {
                        it.transform { user ->
                            user.run {
                                hideLoadingView()
                                hideErrorView()
                                showProfileView()
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
                is Future.Error -> {
                    viewProxy.run {
                        hideLoadingView()
                        hideProfileView()
                        showErrorView()
                    }
                }
                is Future.Proceeding -> {
                    viewProxy.run {
                        hideErrorView()
                        hideProfileView()
                        showLoadingView()
                    }
                }
            }
        }
    }
}
