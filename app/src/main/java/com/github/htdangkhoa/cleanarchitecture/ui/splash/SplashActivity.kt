package com.github.htdangkhoa.cleanarchitecture.ui.splash

import android.os.Bundle
import android.text.TextUtils
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginActivity
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainActivity
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.startActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_splash

    override fun render(savedInstanceState: Bundle?) {
        viewModel.resourceToken.observe(this, object : ObserverResource<AuthResponse.Token>() {
            override fun onSuccess(data: AuthResponse.Token) {
                data.apply {
                    AuthModel.accessToken = accessToken

                    AuthModel.refreshToken = refreshToken
                }

                startActivity<MainActivity>()

                finishAfterTransition()
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let { message ->
                        showDialog("Error", message)
                            .positiveButton {
                                handleHttpError(throwable)
                            }
                    }
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }
        })

        if (TextUtils.isEmpty(AuthModel.refreshToken).not()) {
            viewModel.renewToken(AuthModel.refreshToken!!)
        } else {
            startActivity<LoginActivity>()

            finishAfterTransition()
        }
    }
}
