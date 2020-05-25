package com.github.htdangkhoa.cleanarchitecture.di

import com.github.htdangkhoa.cleanarchitecture.di.module.NetModule
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginModule
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainModule
import com.github.htdangkhoa.cleanarchitecture.ui.splash.SplashModule

object AppComponent {
    val components = listOf(
        NetModule.module,
        SplashModule.module,
        LoginModule.module,
        MainModule.module
    )
}
