package com.github.htdangkhoa.cleanarchitecture.domain.user

import com.github.htdangkhoa.cleanarchitecture.base.BaseUseCase
import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepository

class UserUseCase(
    repository: UserRepository
) : BaseUseCase<UserRepository, Any>(repository) {
    override suspend fun buildUseCase(params: Any?): Result<*> {
        return repository.getMe()
    }
}
