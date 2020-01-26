package com.github.htdangkhoa.cleanarchitecture.extension

import androidx.lifecycle.MutableLiveData

/**
 * Created by khoahuynh on 2020-01-06.
 */
inline fun <reified T> liveDataOf() = MutableLiveData<T>()

inline fun <reified T> mutableLiveDataOf() = liveDataOf<T>()