package com.example.getaaccontributors.feature.detail.profile.contract

import com.example.getaaccontributors.model.Future
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.flow.Flow

interface DetailProfileContract {

    interface Repository {
        suspend fun getUser(userName: String): Flow<Future<UserList.User>>
    }
}
