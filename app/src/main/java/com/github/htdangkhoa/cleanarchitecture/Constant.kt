package com.github.htdangkhoa.cleanarchitecture

import com.chibatching.kotpref.KotprefModel

object Constant : KotprefModel() {
    var BASE_URL: String by stringPref(BuildConfig.BASE_URL)
}
