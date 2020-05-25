package com.github.htdangkhoa.cleanarchitecture.resource

import androidx.lifecycle.Observer

/**
 * Created by khoahuynh on 2020-01-06.
 */
abstract class ObserverResource<T> : Observer<Resource<T>> {
    override fun onChanged(t: Resource<T>) = handleResource(t)

    abstract fun onSuccess(data: T)

    open fun onError(throwable: Throwable?) = Unit

    open fun onLoading(isShow: Boolean) = Unit

    private fun handleResource(resource: Resource<T>) {
        val res = resource

        when (res.status) {
            StatusResource.SUCCESS -> {
                res.data?.let {
                    onSuccess(it)

                    onLoading(false)
                }
            }
            StatusResource.ERROR -> {
                onError(res.throwable)

                onLoading(false)
            }
            StatusResource.LOADING -> {
                onLoading(true)
            }
            else -> {
                onLoading(false)
            }
        }
    }
}
