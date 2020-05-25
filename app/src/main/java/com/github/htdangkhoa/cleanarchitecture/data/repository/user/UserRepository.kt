package com.github.htdangkhoa.cleanarchitecture.data.repository.user

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepository
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.GetMeResponse

interface UserRepository : BaseRepository {
    suspend fun getMe(): Result<GetMeResponse.User?>
}
