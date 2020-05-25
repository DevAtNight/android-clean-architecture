package com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)
