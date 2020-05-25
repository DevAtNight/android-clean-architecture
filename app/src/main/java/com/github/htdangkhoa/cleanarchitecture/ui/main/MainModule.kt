package com.github.htdangkhoa.cleanarchitecture.ui.main

import com.github.htdangkhoa.cleanarchitecture.data.repository.auth.AuthRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.auth.AuthRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.user.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val module = module {
        single(override = true) { provideUserRepository(get()) }

        single(override = true) { provideUserUseCase(get()) }

        single(override = true) { provideAuthRepository(get()) }

        single(override = true) { provideAuthUseCase(get()) }

        viewModel { MainViewModel(get(), get()) }
    }

    private fun provideUserRepository(apiService: ApiService): UserRepository =
            UserRepositoryImp(apiService)

    private fun provideUserUseCase(userRepository: UserRepository) = UserUseCase(userRepository)

    private fun provideAuthRepository(apiService: ApiService): AuthRepository =
            AuthRepositoryImp(apiService)

    private fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(authRepository)
}
