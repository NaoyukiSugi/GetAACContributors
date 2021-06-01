package com.example.getaaccontributors.feature.detail.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.detail.contract.DetailContract
import com.example.getaaccontributors.feature.detail.di.annotation.DetailLifecycleOwner
import com.example.getaaccontributors.feature.detail.presenter.DetailPresenter
import com.example.getaaccontributors.feature.detail.repository.DetailRepository
import com.example.getaaccontributors.feature.detail.view.DetailFragment
import com.example.getaaccontributors.feature.detail.view.DetailViewProxy
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
    fun provideDetailRepository(api: GitHubService): DetailContract.Repository =
        DetailRepository(api)

    @Provides
    @FragmentScoped
    fun provideDetailViewProxy(fragment: Fragment): DetailContract.ViewProxy =
        DetailViewProxy(fragment)

    @Provides
    @FragmentScoped
    fun provideDetailPresenter(
        viewProxy: DetailContract.ViewProxy,
        repository: DetailContract.Repository,
        @DetailLifecycleOwner lifecycleOwner: LifecycleOwner
    ): DetailContract.Presenter =
        DetailPresenter(viewProxy, repository, lifecycleOwner)

}
