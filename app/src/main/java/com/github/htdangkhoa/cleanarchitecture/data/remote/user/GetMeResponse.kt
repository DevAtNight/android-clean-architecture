package com.github.htdangkhoa.cleanarchitecture.data.remote.user

import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class GetMeResponse(
    @SerializedName("data")
    override val data: User?
) : ResponseModel<GetMeResponse.User?>() {
    data class User(
        @SerializedName("firstName")
        val firstName: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("isApproved")
        val isApproved: Boolean?,
        @SerializedName("lastName")
        val lastName: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("role")
        val role: String?,
        @SerializedName("session")
        val session: String?
    )
}
