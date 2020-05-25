package com.github.htdangkhoa.cleanarchitecture.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

/**
 * Created by khoahuynh on 2020-01-06.
 */
fun <T> liveDataOf(default: T? = null) = MutableLiveData<T>().apply { default?.let { value = it } }

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}

fun <T> LiveData<T>.mergeWith(vararg liveDatas: LiveData<T>): LiveData<MutableList<Any?>> {
    val finalInput = mutableListOf<LiveData<T>>()
    finalInput.add(this)
    finalInput.addAll(liveDatas)

    return merge(finalInput)
}

fun <T> merge(liveDataList: List<LiveData<T>>): LiveData<MutableList<Any?>> {
    val list = mutableListOf<Any?>()

    liveDataList.forEach { liveData ->
        liveData.value?.let {
            if (it is Collection<*>) {
                list.addAll(it)
            } else {
                list.add(it)
            }
        }
    }

    return liveDataOf(list)
}

fun <A, B> LiveData<A>.combineLatest(second: LiveData<B>) =
    combineLatest(this, second) { a, b -> Pair(a, b) }

fun <A, B, C> combineLatest(
    first: LiveData<A>,
    second: LiveData<B>,
    block: (A?, B?) -> C
): LiveData<C> {
    val finalLiveData = MediatorLiveData<C>()

    var firstEmitted = false
    var firstValue: A? = null

    var secondEmitted = false
    var secondValue: B? = null

    val combine: () -> Unit = {
        if (firstEmitted && secondEmitted) {
            finalLiveData.postValue(block(firstValue, secondValue))
        }
    }

    with(finalLiveData) {
        addSource(first) {
            firstEmitted = true
            firstValue = it

            combine()
        }

        addSource(second) {
            secondEmitted = true
            secondValue = it

            combine()
        }
    }

    return finalLiveData
}

fun <A, B, C> LiveData<A>.combineLatest(second: LiveData<B>, third: LiveData<C>) =
    combineLatest(this, second, third) { a, b, c -> Triple(a, b, c) }

fun <A, B, C, D> combineLatest(
    first: LiveData<A>,
    second: LiveData<B>,
    third: LiveData<C>,
    block: (A?, B?, C?) -> D
): LiveData<D> {
    val finalLiveData = MediatorLiveData<D>()

    var firstEmitted = false
    var firstValue: A? = null

    var secondEmitted = false
    var secondValue: B? = null

    var thirdEmitted = false
    var thirdValue: C? = null

    val combine: () -> Unit = {
        if (firstEmitted && secondEmitted && thirdEmitted) {
            finalLiveData.postValue(block(firstValue, secondValue, thirdValue))
        }
    }

    with(finalLiveData) {
        addSource(first) {
            firstEmitted = true
            firstValue = it

            combine()
        }

        addSource(second) {
            secondEmitted = true
            secondValue = it

            combine()
        }

        addSource(third) {
            thirdEmitted = true
            thirdValue = it

            combine()
        }
    }

    return finalLiveData
}
