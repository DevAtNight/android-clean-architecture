package com.github.htdangkhoa.cleanarchitecture.data.repository.user

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.GetMeResponse
import com.github.htdangkhoa.cleanarchitecture.data.service.AppService
import com.github.htdangkhoa.cleanarchitecture.extension.map
import retrofit2.HttpException

class UserRepositoryImp(
    appService: AppService
): BaseRepositoryImp(appService), UserRepository {
    override suspend fun getMe(): Result<GetMeResponse.User?> {
        return try {
            val res = appService.getMe()

            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}