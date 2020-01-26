package com.github.htdangkhoa.cleanarchitecture.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseExceptionModel
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginActivity
import com.pawegio.kandroid.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import kotlin.reflect.KClass

@SuppressLint("Registered")
abstract class BaseActivity<VM: ViewModel>(clazz: KClass<VM>): AppCompatActivity() {
    abstract val layoutRedID: Int

    val viewModel: VM by viewModel(clazz)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRedID)

        render(savedInstanceState)
    }

    open fun render(savedInstanceState: Bundle?) = Unit

    fun defaultErrorHandler(throwable: Throwable? = null, block: ((Throwable?) -> Unit)? = null) {
        when (throwable) {
            is HttpException -> {
                logout(throwable.code())
            }
            is ResponseExceptionModel -> {
                throwable.responseModel?.code?.let { logout(it) }
            }
        }

        block?.invoke(throwable)
    }

    private fun logout(code: Int) {
        if (code == 401) {
            AuthModel.clear()

            startActivity<LoginActivity>()

            finishAfterTransition()
        }
    }
}