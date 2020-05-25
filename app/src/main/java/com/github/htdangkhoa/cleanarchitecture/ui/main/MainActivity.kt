package com.github.htdangkhoa.cleanarchitecture.ui.main

import android.os.Bundle
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.GetMeResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_main

    override fun render(savedInstanceState: Bundle?) {
        viewModel.resourceUser.observe(this, object : ObserverResource<GetMeResponse.User>() {
            override fun onSuccess(data: GetMeResponse.User) {
                data.apply {
                    txtPhone.text = "Phone: $phone"

                    txtFirstName.text = "First name: $firstName"

                    txtLastName.text = "Last name: $lastName"
                }
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let { toast(it) }

                    handleHttpError(it)
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }
        })

        viewModel.getMe()

        viewModel.resourceLogout.observe(this, object : ObserverResource<String>() {
            override fun onSuccess(data: String) {
                logout(401)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog("Error", it)
                    }
                }
            }
        })

        btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}
