package com.example.getaaccontributors.feature.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.getaaccontributors.R
import com.example.getaaccontributors.feature.home.contract.HomeContract
import com.example.getaaccontributors.model.UserList

class HomeAdapter : PagingDataAdapter<UserList.User, HomeViewHolder>(DIFF_CALLBACK) {

    var listener: HomeContract.UserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return createHomeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getUser(position)?.run { holder.bind(this, listener) }
    }

    @VisibleForTesting
    internal fun createHomeViewHolder(parent: ViewGroup): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_home_user, parent, false)
        return HomeViewHolder(view)
    }

    @VisibleForTesting
    internal fun getUser(position: Int) = getItem(position)

    companion object {
        @VisibleForTesting
        internal val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserList.User>() {

            override fun areItemsTheSame(
                oldItem: UserList.User,
                newItem: UserList.User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserList.User,
                newItem: UserList.User
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
