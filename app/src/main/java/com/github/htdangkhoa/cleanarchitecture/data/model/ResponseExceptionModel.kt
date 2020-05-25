package com.github.htdangkhoa.cleanarchitecture.data.model

class ResponseExceptionModel(
    val responseModel: ResponseModel<*>?
) : Exception(responseModel?.error?.message) {
    inline fun <reified T> getData() = responseModel as ResponseModel<T>?
}
