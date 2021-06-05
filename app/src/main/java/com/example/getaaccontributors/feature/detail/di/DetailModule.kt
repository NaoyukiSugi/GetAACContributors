package com.example.getaaccontributors.feature.detail.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.detail.di.annotation.DetailProfileLifecycleOwner
import com.example.getaaccontributors.feature.detail.di.annotation.DetailReposLifecycleOwner
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.feature.detail.profile.presenter.DetailProfilePresenter
import com.example.getaaccontributors.feature.detail.profile.repository.DetailProfileRepository
import com.example.getaaccontributors.feature.detail.profile.view.DetailProfileViewProxy
import com.example.getaaccontributors.feature.detail.repos.presenter.DetailReposPresenter
import com.example.getaaccontributors.feature.detail.repos.repository.DetailReposRepository
import com.example.getaaccontributors.feature.detail.fragment.DetailFragment
import com.example.getaaccontributors.feature.detail.repos.view.DetailReposViewProxy
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

    // region DetailRepos
    @Provides
    @Reusable
    fun provideDetailReposRepository(api: GitHubService): DetailReposContract.Repository =
        DetailReposRepository(api)

    @Provides
    @FragmentScoped
    fun provideDetailReposViewProxy(fragment: Fragment): DetailReposContract.ViewProxy =
        DetailReposViewProxy(fragment)

    @Provides
    @DetailReposLifecycleOwner
    fun provideDetailReposLifecycleOwner(fragment: Fragment): LifecycleOwner = fragment

    @Provides
    @FragmentScoped
    fun provideDetailReposPresenter(
        viewProxy: DetailReposContract.ViewProxy,
        repository: DetailReposContract.Repository,
        @DetailReposLifecycleOwner lifecycleOwner: LifecycleOwner
    ): DetailReposContract.Presenter =
        DetailReposPresenter(viewProxy, repository, lifecycleOwner)
    // endregion

    // region DetailProfile
    @Provides
    @Reusable
    fun provideDetailProfileRepository(service: GitHubService): DetailProfileContract.Repository =
        DetailProfileRepository(service)

    @Provides
    @FragmentScoped
    fun provideDetailProfileViewProxy(fragment: Fragment): DetailProfileContract.ViewProxy =
        DetailProfileViewProxy(fragment)

    @Provides
    @DetailProfileLifecycleOwner
    fun provideDetailProfileLifecycleOwner(fragment: Fragment): LifecycleOwner = fragment

    @Provides
    @FragmentScoped
    fun provideDetailProfilePresenter(
        viewProxy: DetailProfileContract.ViewProxy,
        repository: DetailProfileContract.Repository,
        @DetailProfileLifecycleOwner lifecycleOwner: LifecycleOwner
    ): DetailProfileContract.Presenter =
        DetailProfilePresenter(viewProxy, repository, lifecycleOwner)
    // endregion
}
