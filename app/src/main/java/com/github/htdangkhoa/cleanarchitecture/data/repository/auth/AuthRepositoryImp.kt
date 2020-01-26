package com.github.htdangkhoa.cleanarchitecture.data.repository.auth

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.cleanarchitecture.data.service.AppService
import com.github.htdangkhoa.cleanarchitecture.extension.map

class AuthRepositoryImp(
    appService: AppService
): BaseRepositoryImp(appService), AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<AuthResponse.Token?> {
        return try {
            val res = appService.login(loginRequest)

            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun renewToken(renewTokenRequest: RenewTokenRequest): Result<AuthResponse.Token?> {
        return try {
            val res = appService.renewToken(renewTokenRequest)

            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}