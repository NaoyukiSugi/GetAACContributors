package com.example.getaaccontributors.feature.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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

        override fun loadIconImage(imageUrl: String) {
            TODO("Not yet implemented")
        }

        override fun setName(name: String) {
            TODO("Not yet implemented")
        }

        override fun setContributions(contributions: String) {
            TODO("Not yet implemented")
        }

        override fun setOnUserClickListener(
            user: UserList.User,
            listener: HomeContract.UserClickListener?
        ) {
            TODO("Not yet implemented")
        }
    }
}
