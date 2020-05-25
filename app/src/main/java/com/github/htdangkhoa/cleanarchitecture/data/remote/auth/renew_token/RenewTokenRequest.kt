package com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token

import com.google.gson.annotations.SerializedName

data class RenewTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)
