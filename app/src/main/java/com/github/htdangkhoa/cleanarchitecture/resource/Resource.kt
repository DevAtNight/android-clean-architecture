package com.github.htdangkhoa.cleanarchitecture.resource

data class Resource<T>(
    val status: StatusResource = StatusResource.NOTHING,
    val data: T?,
    val throwable: Throwable? = null
) {
    companion object {
        inline fun <reified T> success(data: T) = Resource(StatusResource.SUCCESS, data)

        inline fun <reified T> error(
            throwable: Throwable
        ) = Resource<T>(StatusResource.ERROR, null, throwable)

        inline fun <reified T> loading() = Resource<T>(StatusResource.LOADING, null, null)

        inline fun <reified T> nothing() = Resource<T>(StatusResource.NOTHING, null, null)
    }
}
