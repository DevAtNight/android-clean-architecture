package com.github.htdangkhoa.cleanarchitecture.extension

import timber.log.Timber

/**
 * Created by khoahuynh on 2020-01-06.
 */
fun debug(msg: Any?) = Timber.d("$msg")

fun info(msg: Any?) = Timber.i("$msg")

fun warning(msg: Any?) = Timber.w("$msg")

fun verbose(msg: Any?) = Timber.v("$msg")

fun error(msg: Any?) = Timber.e("$msg")
