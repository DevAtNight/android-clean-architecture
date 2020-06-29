package com.github.htdangkhoa.cleanarchitecture.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.KeyEvent
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.AppUtils
import com.chibatching.kotpref.blockingBulk
import com.github.htdangkhoa.cleanarchitecture.BuildConfig
import com.github.htdangkhoa.cleanarchitecture.Constant
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseExceptionModel
import com.github.htdangkhoa.cleanarchitecture.extension.appName
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginActivity
import com.github.nisrulz.sensey.Sensey
import com.github.nisrulz.sensey.ShakeDetector
import com.pawegio.kandroid.startActivity
import com.pawegio.kandroid.toast
import kotlin.reflect.KClass
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

@SuppressLint("Registered")
abstract class BaseActivity<VM : ViewModel>(clazz: KClass<VM>) : AppCompatActivity() {
    @get:LayoutRes
    abstract val layoutResID: Int

    protected val viewModel: VM by viewModel(clazz)

    private var dialogEnterHost: MaterialDialog? = null

    private val shakeListener: ShakeDetector.ShakeListener by lazy {
        object : ShakeDetector.ShakeListener {
            override fun onShakeDetected() {
                dialogEnterHost?.dismiss()

                dialogEnterHost = MaterialDialog(this@BaseActivity).show {
                    noAutoDismiss()

                    title(text = "Enter host")

                    input(
                        hint = "Type a URL. Ex: http://abc.com/",
                        prefill = Constant.BASE_URL,
                        inputType = InputType.TYPE_TEXT_VARIATION_URI
                    ) { _, text ->
                        if ("$text".toHttpUrlOrNull() == null) {
                            toast("Host is invalid!")

                            return@input
                        } else if (!"$text".endsWith("/")) {
                            toast("Host must be end with \"/\" character!")

                            return@input
                        }

                        if (Constant.BASE_URL == getInputField().text.toString()) {
                            dismiss()

                            return@input
                        }

                        Constant.blockingBulk { BASE_URL = "$text" }

                        toast("$appName is restarting...")

                        Handler(mainLooper).postDelayed({ AppUtils.relaunchApp(true) }, 1200)
                    }

                    onShow { getInputField().selectAll() }

                    positiveButton(text = "Save")

                    negativeButton(text = "Cancel") { dismiss() }
                }
            }

            override fun onShakeStopped() = Unit
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResID)

        if (BuildConfig.DEBUG) {
            Sensey.getInstance()
                .startShakeDetection(
                    20F,
                    500,
                    shakeListener
                )
        }

        render(savedInstanceState)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            shakeListener.onShakeDetected()

            return true
        }

        return super.onKeyUp(keyCode, event)
    }

    protected open fun render(savedInstanceState: Bundle?) = Unit

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

            if (this::class.simpleName != LoginActivity::class.simpleName) {
                startActivity<LoginActivity>()

                finishAfterTransition()
            }
        }
    }

    protected fun showDialog(title: String? = "Info", message: String? = null): MaterialDialog {
        return MaterialDialog(this).show {
            title(text = title)

            message(text = message)

            positiveButton(text = "OK")
        }
    }
}
