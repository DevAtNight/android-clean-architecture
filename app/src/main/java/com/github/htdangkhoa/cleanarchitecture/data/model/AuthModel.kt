package com.github.htdangkhoa.cleanarchitecture.data.model

import com.chibatching.kotpref.KotprefModel

object AuthModel : KotprefModel() {
    var accessToken: String? by nullableStringPref()

    var refreshToken: String? by nullableStringPref()
}
