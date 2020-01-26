package com.github.htdangkhoa.cleanarchitecture.domain.auth

import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest

class AuthParam private constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val SIGN_UP = 1

            const val LOGIN = 2

            const val RENEW_TOKEN = 3
        }
    }

    lateinit var loginRequest: LoginRequest

    lateinit var renewTokenRequest: RenewTokenRequest

    constructor(loginRequest: LoginRequest): this(Type.LOGIN) {
        this.loginRequest = loginRequest
    }

    constructor(renewTokenRequest: RenewTokenRequest): this(Type.RENEW_TOKEN) {
        this.renewTokenRequest = renewTokenRequest
    }
}