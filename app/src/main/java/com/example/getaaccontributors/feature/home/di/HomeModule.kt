package com.example.getaaccontributors.feature.home.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.feature.home.di.annotation.HomeLifecycleOwner
import com.example.getaaccontributors.feature.home.presenter.HomePresenter
import com.example.getaaccontributors.feature.home.repository.HomeRepository
import com.example.getaaccontributors.feature.home.view.HomeFragment
import com.example.getaaccontributors.feature.home.view.HomeViewProxy
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class HomeModule {

    @Provides
    fun provideUserListFragment(fragment: Fragment): HomeFragment =
        fragment as HomeFragment

    @Provides
    @HomeLifecycleOwner
    fun provideLifecycleOwner(fragment: Fragment): LifecycleOwner = fragment

    @Provides
    @Reusable
    fun provideUserListRepository(api: GitHubService): HomeContract.Repository =
        HomeRepository(api)

    @Provides
    @FragmentScoped
    fun provideUserListViewProxy(fragment: Fragment): HomeContract.ViewProxy =
        HomeViewProxy(fragment)

    @Provides
    @FragmentScoped
    fun provideUserListPresenter(
        viewProxy: HomeContract.ViewProxy,
        repository: HomeContract.Repository,
        @HomeLifecycleOwner lifecycleOwner: LifecycleOwner
    ): HomeContract.Presenter =
        HomePresenter(viewProxy, repository, lifecycleOwner)
}
