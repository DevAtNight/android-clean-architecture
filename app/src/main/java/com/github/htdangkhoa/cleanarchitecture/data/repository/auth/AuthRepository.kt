package com.github.htdangkhoa.cleanarchitecture.data.repository.auth

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepository
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest

interface AuthRepository : BaseRepository {
    suspend fun login(
        loginRequest: LoginRequest
    ): Result<AuthResponse.Token?>

    suspend fun renewToken(
        renewTokenRequest: RenewTokenRequest
    ): Result<AuthResponse.Token?>

    suspend fun logout(): Result<String>
}
