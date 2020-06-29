package com.github.htdangkhoa.cleanarchitecture.extension

import android.content.Context
import com.github.htdangkhoa.cleanarchitecture.R

val Context.appName: String
    get() = this.resources.getString(R.string.app_name)
