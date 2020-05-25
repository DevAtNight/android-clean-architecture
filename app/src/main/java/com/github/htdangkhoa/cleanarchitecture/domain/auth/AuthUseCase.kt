package com.github.htdangkhoa.cleanarchitecture.domain.auth

import com.github.htdangkhoa.cleanarchitecture.base.BaseUseCase
import com.github.htdangkhoa.cleanarchitecture.data.repository.auth.AuthRepository

class AuthUseCase(
    repository: AuthRepository
) : BaseUseCase<AuthRepository, AuthParam>(repository) {
    override suspend fun buildUseCase(params: AuthParam?): Result<*> {
        return when (params?.type) {
            AuthParam.Type.LOGIN -> repository.login(params.loginRequest)

            AuthParam.Type.RENEW_TOKEN -> repository.renewToken(params.renewTokenRequest)

            AuthParam.Type.LOGOUT -> repository.logout()

            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
