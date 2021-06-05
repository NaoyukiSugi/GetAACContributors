package com.example.getaaccontributors.feature.detail.di

import androidx.fragment.app.Fragment
import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.detail.repos.contract.DetailReposContract
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.feature.detail.profile.presenter.DetailProfilePresenter
import com.example.getaaccontributors.feature.detail.profile.repository.DetailProfileRepository
import com.example.getaaccontributors.feature.detail.profile.view.DetailProfileViewProxy
import com.example.getaaccontributors.feature.detail.repos.presenter.DetailReposPresenter
import com.example.getaaccontributors.feature.detail.repos.repository.DetailReposRepository
import com.example.getaaccontributors.feature.detail.repos.view.DetailFragment
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

    @Provides
    @Reusable
    fun provideDetailRepository(api: GitHubService): DetailReposContract.Repository =
        DetailReposRepository(api)

    @Provides
    @FragmentScoped
    fun provideDetailViewProxy(fragment: Fragment): DetailReposContract.ViewProxy =
        DetailReposViewProxy(fragment)

    @Provides
    @FragmentScoped
    fun provideDetailPresenter(
        viewProxy: DetailReposContract.ViewProxy,
        repository: DetailReposContract.Repository
    ): DetailReposContract.Presenter =
        DetailReposPresenter(viewProxy, repository)

    @Provides
    @Reusable
    fun provideDetailProfileRepository(service: GitHubService): DetailProfileContract.Repository =
        DetailProfileRepository(service)

    @Provides
    @FragmentScoped
    fun provideDetailProfileViewProxy(fragment: Fragment): DetailProfileContract.ViewProxy =
        DetailProfileViewProxy(fragment)

    @Provides
    @FragmentScoped
    fun provideDetailProfilePresenter(
        viewProxy: DetailProfileContract.ViewProxy,
        repository: DetailProfileContract.Repository
    ): DetailProfileContract.Presenter =
        DetailProfilePresenter(viewProxy, repository)
}
