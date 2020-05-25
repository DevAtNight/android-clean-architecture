package com.github.htdangkhoa.cleanarchitecture.data.remote.auth

import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    override val data: Token?
) : ResponseModel<AuthResponse.Token?>() {
    data class Token(
        @SerializedName("accessToken")
        val accessToken: String,

        @SerializedName("refreshToken")
        val refreshToken: String
    )
}
