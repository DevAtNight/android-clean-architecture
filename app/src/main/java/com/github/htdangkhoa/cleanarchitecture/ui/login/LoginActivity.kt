package com.github.htdangkhoa.cleanarchitecture.ui.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.resource.Status
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainActivity
import com.pawegio.kandroid.startActivity
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {
    override fun render(savedInstanceState: Bundle?) {
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            var phoneNumberState by remember { mutableStateOf("") }
            var passwordState by remember { mutableStateOf("") }
            var isLoadingState by remember { mutableStateOf(false) }

            Scaffold(scaffoldState = scaffoldState) { paddingValues ->
                LoginScreen(
                    phoneNumberState = phoneNumberState,
                    passwordState = passwordState,
                    isLoading = isLoadingState,
                    onPhoneNumberChange = { phoneNumberState = it },
                    onPasswordChange = { passwordState = it },
                    onLoginClick = {
                        viewModel.login(phoneNumberState, passwordState)
                    }
                )
            }

            viewModel.resourceToken.observe(this, object : ObserverResource<AuthResponse.Token>() {
                override fun onSuccess(data: AuthResponse.Token) {
                    isLoadingState = false
                    data.apply {
                        AuthModel.accessToken = accessToken
                        AuthModel.refreshToken = refreshToken
                    }
                    startActivity<MainActivity>()
                    finishAfterTransition()
                }

                override fun onError(throwable: Throwable?) {
                    isLoadingState = false
                    handleError(throwable) {
                        it?.message?.let { errorMessage ->
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = errorMessage,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }

                override fun onLoading(isShow: Boolean) {
                    isLoadingState = isShow
                }
            })
        }
    }
}
