package com.github.htdangkhoa.cleanarchitecture.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.afollestad.materialdialogs.MaterialDialog
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseExceptionModel
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginActivity
import com.pawegio.kandroid.startActivity
import kotlin.reflect.KClass
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

/**
 * Created by khoahuynh on 2020-02-12.
 */
abstract class BaseFragment<VM : ViewModel>(val clazz: KClass<VM>) : Fragment() {
    @get:LayoutRes
    abstract val layoutResID: Int

    protected val viewModel: VM by viewModel(clazz)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResID, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        render(view, savedInstanceState)
    }

    protected open fun render(view: View, savedInstanceState: Bundle?) = Unit

    protected fun handleError(throwable: Throwable? = null, block: ((Throwable?) -> Unit)? = null) {
        return block?.invoke(throwable) ?: handleHttpError(throwable)
    }

    protected fun handleHttpError(throwable: Throwable?) {
        when (throwable) {
            is HttpException -> {
                logout(throwable.code())
            }
            is ResponseExceptionModel -> {
                throwable.responseModel?.code?.let { logout(it) }
            }
        }
    }

    protected fun logout(code: Int) {
        if (code == 401) {
            AuthModel.clear()

            context?.let {
                if (it is Activity && it::class.simpleName != LoginActivity::class.simpleName) {
                    it.startActivity<LoginActivity>()

                    it.finishAfterTransition()
                }
            }
        }
    }

    protected fun showDialog(title: String? = "Info", message: String? = null): MaterialDialog {
        return MaterialDialog(context!!).show {
            title(text = title)

            message(text = message)

            positiveButton(text = "OK")
        }
    }
}
