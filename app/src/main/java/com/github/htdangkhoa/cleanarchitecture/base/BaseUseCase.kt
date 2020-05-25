package com.github.htdangkhoa.cleanarchitecture.base

import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseExceptionModel
import com.github.htdangkhoa.cleanarchitecture.resource.OnHandle
import com.github.htdangkhoa.cleanarchitecture.resource.ResourceHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseUseCase<Repository : BaseRepository, Params : Any>(val repository: Repository) {
    var parentJob = SupervisorJob()

    val backgroundContext = Dispatchers.IO

    val foregroundContext = Dispatchers.Main

    @PublishedApi
    internal abstract suspend fun buildUseCase(params: Params? = null): Result<*>

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> execute(params: Params? = null, block: OnHandle<T>) {
        val response = ResourceHandler<T>()
            .apply { block() }

        unsubscribe()

        parentJob = SupervisorJob()

        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    buildUseCase(params)
                } as Result<T>

                response(result.getOrThrow())
            } catch (cancellation: CancellationException) {
                response(cancellation)
            } catch (exception: ResponseExceptionModel) {
                response(exception)
            } catch (exception: Exception) {
                response(exception)
            }
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()

            cancel()
        }
    }
}
