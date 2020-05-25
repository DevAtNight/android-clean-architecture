package com.github.htdangkhoa.cleanarchitecture.ui.login

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    val resourceToken = liveDataOf<Resource<AuthResponse.Token>>()

    fun login(phone: String, password: String) {
        val request = LoginRequest(
            phone
                .replace("[^0-9]", "", true)
                .prependIndent("+"),
            password
        )

        resourceToken.postValue(Resource.loading())

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
