package com.github.htdangkhoa.cleanarchitecture.data.service

import com.github.htdangkhoa.cleanarchitecture.data.remote.SuccessResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.GetMeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): AuthResponse

    @POST("auth/renew-token")
    suspend fun renewToken(
        @Body renewTokenRequest: RenewTokenRequest
    ): AuthResponse

    @GET("me")
    suspend fun getMe(): GetMeResponse

    @GET("auth/logout")
    suspend fun logout(): SuccessResponse
}
