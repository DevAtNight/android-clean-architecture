package com.github.htdangkhoa.cleanarchitecture.base

import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseExceptionModel
import com.github.htdangkhoa.cleanarchitecture.util.CompletionBlock
import com.github.htdangkhoa.cleanarchitecture.util.Request
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseUseCase<Repository: BaseRepository, Params: Any>(val repository: Repository) {
    var parentJob = SupervisorJob()

    val backgroundContext = Dispatchers.IO

    val foregroundContext = Dispatchers.Main

    @PublishedApi
    internal abstract suspend fun buildUseCase(params: Params? = null): Result<*>

    inline fun <reified T> execute(params: Params? = null, block: CompletionBlock<T>) {
        val response = Request<T>()
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
            }
        }
    }

    fun unsubscribe() {
        parentJob .apply {
            cancelChildren()

            cancel()
        }
    }
}