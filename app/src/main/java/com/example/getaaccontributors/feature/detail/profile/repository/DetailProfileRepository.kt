package com.example.getaaccontributors.feature.detail.profile.repository

import com.example.getaaccontributors.api.github.GitHubService
import com.example.getaaccontributors.feature.detail.profile.contract.DetailProfileContract
import com.example.getaaccontributors.model.Future
import com.example.getaaccontributors.model.UserList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import javax.inject.Inject

class DetailProfileRepository @Inject constructor(
    private val service: GitHubService
) : DetailProfileContract.Repository {

    override suspend fun getUser(userName: String): Flow<Future<UserList.User>> =
        flow<Future<UserList.User>> {
            val response = service.getUser(userName)
            if (response.isSuccessful) emit(Future.Success(response.body()!!))
            else throw HttpException(response)
        }.catch {
            emit(Future.Error(it))
        }.onStart {
            emit(Future.Proceeding)
        }.flowOn(Dispatchers.IO)
}
