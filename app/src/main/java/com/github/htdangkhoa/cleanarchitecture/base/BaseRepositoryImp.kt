package com.github.htdangkhoa.cleanarchitecture.base

import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService

abstract class BaseRepositoryImp(val apiService: ApiService) : BaseRepository
