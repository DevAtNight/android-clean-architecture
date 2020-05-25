package com.github.htdangkhoa.cleanarchitecture.ui.splash

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class SplashViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    val resourceToken = liveDataOf<Resource<AuthResponse.Token>>()

    fun renewToken(refreshToken: String) {
        resourceToken.postValue(Resource.loading())

        val request = RenewTokenRequest(refreshToken)

        authUseCase.execute<AuthResponse.Token>(AuthParam(request)) {
            onComplete {
                resourceToken.postValue(Resource.success(it))
            }

            onError {
                resourceToken.postValue(Resource.error(it))
            }

            onCancel {
                resourceToken.postValue(Resource.error(it))
            }
        }
    }
}
