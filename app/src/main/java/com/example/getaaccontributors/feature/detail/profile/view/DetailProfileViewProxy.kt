package com.example.getaaccontributors.feature.detail.profile.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import javax.inject.Inject

class DetailProfileViewProxy @VisibleForTesting internal constructor(
    private val fragment: Fragment,
    private val requestManager: RequestManager
) : DetailProfileContract.ViewProxy {

    @Inject
    constructor(fragment: Fragment) : this(fragment, Glide.with(fragment))

    private val profileView: View?
        get() = fragment.view?.findViewById(R.id.detail_profile_view)

    private val progressBar: ContentLoadingProgressBar?
        get() = fragment.view?.findViewById(R.id.progress_bar)

    private val errorView: View?
        get() = fragment.view?.findViewById(R.id.error_view)

    private val userIconIV: ImageView?
        get() = fragment.view?.findViewById(R.id.user_icon)

    private val userNameTv: TextView?
        get() = fragment.view?.findViewById(R.id.user_name)

    private val realNameTv: TextView?
        get() = fragment.view?.findViewById(R.id.real_name)

    private val locationTv: TextView?
        get() = fragment.view?.findViewById(R.id.location)

    private val companyTv: TextView?
        get() = fragment.view?.findViewById(R.id.company)

    private val blogTv: TextView?
        get() = fragment.view?.findViewById(R.id.blog)

    private val twitterUserNameTv: TextView?
        get() = fragment.view?.findViewById(R.id.twitter_user_name)

    override fun showProfileView() {
        profileView?.isVisible = true
    }

    override fun hideProfileView() {
        profileView?.isVisible = false
    }

    override fun showErrorView() {
        errorView?.isVisible = true
    }

    override fun hideErrorView() {
        errorView?.isVisible = false
    }

    override fun showLoadingView() {
        progressBar?.isVisible = true
    }

    override fun hideLoadingView() {
        progressBar?.isVisible = false
    }

    override fun loadIconImage(imageUrl: String) {
        userIconIV?.let { requestManager.load(imageUrl).into(it) }
    }

    override fun setUserName(userName: String) {
        userNameTv?.text = userName
    }

    override fun setRealName(realName: String) {
        realNameTv?.text = realName
    }

    override fun setLocation(location: String) {
        locationTv?.text = location
    }

    override fun setCompany(company: String) {
        companyTv?.text = company
    }

    override fun setBlog(blog: String) {
        blogTv?.text = blog
    }

    override fun setTwitterUserName(twitterUserName: String) {
        twitterUserNameTv?.text = twitterUserName
    }
}
