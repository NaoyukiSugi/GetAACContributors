package com.example.getaaccontributors.feature.home.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList

class HomeViewHolder(
    private val view: View,
    private val viewProxy: HomeContract.ViewHolderViewProxy = ViewProxy(view)
) : RecyclerView.ViewHolder(view) {

    fun bind(user: UserList.User, listener: HomeContract.UserClickListener?) {
        viewProxy.run {
            loadIconImage(user.avatarUrl)
            setName(user.login)
            setContributions(user.contributions.toString())
            setOnUserClickListener(user, listener)
        }
    }

    internal class ViewProxy(
        private val view: View,
        private val requestManager: RequestManager = Glide.with(view)
    ) : HomeContract.ViewHolderViewProxy {

        private val iconIv: ImageView
            get() = view.findViewById(R.id.icon)

        private val nameTv: TextView
            get() = view.findViewById(R.id.name)

        private val contributionsTv: TextView
            get() = view.findViewById(R.id.contributions)

        override fun loadIconImage(imageUrl: String) {
            requestManager.load(imageUrl).into(iconIv)
        }

        override fun setName(name: String) {
            nameTv.text = name
        }

        override fun setContributions(contributions: String) {
            contributionsTv.text = contributions
        }

        override fun setOnUserClickListener(
            user: UserList.User,
            listener: HomeContract.UserClickListener?
        ) {
            view.setOnClickListener { listener?.onUserClick(user) }
        }
    }
}
