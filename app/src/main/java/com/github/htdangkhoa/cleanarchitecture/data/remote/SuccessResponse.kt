package com.github.htdangkhoa.cleanarchitecture.data.remote

import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("data")
    override val data: String = "Success."
) : ResponseModel<String>()
