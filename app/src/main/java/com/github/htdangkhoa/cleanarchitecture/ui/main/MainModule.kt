package com.github.htdangkhoa.cleanarchitecture.ui.main

import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.service.AppService
import com.github.htdangkhoa.cleanarchitecture.domain.user.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val module = module {
        single(override = true) { provideUserRepository(get()) }

        single (override = true){ provideUserUseCase(get()) }

        viewModel { MainViewModel(get()) }
    }

    private fun provideUserRepository(appService: AppService): UserRepository = UserRepositoryImp(appService)

    private fun provideUserUseCase(userRepository: UserRepository) = UserUseCase(userRepository)
}