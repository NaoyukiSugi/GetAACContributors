package com.example.getaaccontributors.feature.detail.profile.view

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract

class DetailProfileViewProxy @VisibleForTesting internal constructor(
    private val fragment: Fragment,
    private val requestManager: RequestManager
) : DetailProfileContract.ViewProxy {

    constructor(fragment: Fragment) : this(fragment, Glide.with(fragment))

    private val userIconIV: ImageView?
        get() = fragment.view?.findViewById(R.id.icon)

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
