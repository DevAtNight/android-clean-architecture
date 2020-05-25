package com.github.htdangkhoa.cleanarchitecture.data.model

import com.google.gson.annotations.SerializedName

abstract class ResponseModel<T : Any?> {
    data class Error(
        @SerializedName("message")
        val message: String?
    )

    @SerializedName("code")
    val code: Int = 200

    abstract val data: T?

    @SerializedName("error")
    val error: Error? = null
}
