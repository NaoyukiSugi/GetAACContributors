package com.example.getaaccontributors.feature.detail.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.feature.detail.di.annotation.DetailLifecycleOwner
import com.example.getaaccontributors.feature.detail.repos.presenter.DetailReposPresenter
import com.example.getaaccontributors.feature.detail.repos.repository.DetailReposRepository
import com.example.getaaccontributors.feature.detail.repos.view.DetailFragment
import com.example.getaaccontributors.feature.detail.repos.view.DetailViewReposProxy
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class DetailModule {

    @Provides
    fun provideDetailFragment(fragment: Fragment): DetailFragment =
        fragment as DetailFragment

    @Provides
    @DetailLifecycleOwner
    fun provideLifecycleOwner(fragment: Fragment): LifecycleOwner = fragment

    @Provides
    @Reusable
    fun provideDetailRepository(api: GitHubService): DetailReposContract.Repository =
        DetailReposRepository(api)

    @Provides
    @FragmentScoped
    fun provideDetailViewProxy(fragment: Fragment): DetailReposContract.ViewProxy =
        DetailViewReposProxy(fragment)

    @Provides
    @FragmentScoped
    fun provideDetailPresenter(
        viewProxy: DetailReposContract.ViewProxy,
        repository: DetailReposContract.Repository,
        @DetailLifecycleOwner lifecycleOwner: LifecycleOwner
    ): DetailReposContract.Presenter =
        DetailReposPresenter(viewProxy, repository, lifecycleOwner)

}
