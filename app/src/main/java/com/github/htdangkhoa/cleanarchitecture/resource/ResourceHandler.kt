package com.github.htdangkhoa.cleanarchitecture.resource

import kotlinx.coroutines.CancellationException

typealias OnHandle<T> = ResourceHandler<T>.() -> Unit

typealias OnComplete<T> = (T) -> Unit

typealias OnError = (Throwable) -> Unit

typealias OnCancel = (CancellationException) -> Unit

class ResourceHandler<T> {
    private var onComplete: OnComplete<T>? = null

    private var onError: OnError? = null

    private var onCancel: OnCancel? = null

    fun onComplete(block: OnComplete<T>) {
        onComplete = block
    }

    fun onError(block: OnError) {
        onError = block
    }

    fun onCancel(block: OnCancel) {
        onCancel = block
    }

    operator fun invoke(result: T) {
        onComplete?.let {
            it.invoke(result)
        }
    }

    operator fun invoke(error: Exception) {
        onError?.let {
            it.invoke(error)
        }
    }

    operator fun invoke(error: CancellationException) {
        onCancel?.let {
            it.invoke(error)
        }
    }
}
