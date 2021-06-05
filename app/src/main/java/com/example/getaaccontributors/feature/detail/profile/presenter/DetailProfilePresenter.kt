package com.example.getaaccontributors.feature.detail.profile.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.model.Future
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailProfilePresenter @Inject constructor(
    private val viewProxy: DetailProfileContract.ViewProxy,
    private val repository: DetailProfileContract.Repository,
    lifecycleOwner: LifecycleOwner
) : DetailProfileContract.Presenter,
    LifecycleObserver,
    CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()

    override fun getUser(userName: String) {
        launch {
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
                                    setRealName(name ?: DEFAULT_PARAM)
                                    setLocation(location ?: DEFAULT_PARAM)
                                    setCompany(company ?: DEFAULT_PARAM)
                                    setBlog(blog ?: DEFAULT_PARAM)
                                    setTwitterUserName(twitterUsername ?: DEFAULT_PARAM)
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

    companion object {
        private const val DEFAULT_PARAM = ""
    }
}
