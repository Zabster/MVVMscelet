package com.zabster.mvvmscelet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zabster.mvvmscelet.di.appModule
import com.zabster.mvvmscelet.ui.login.LoginViewModel
import com.zabster.mvvmscelet.ui.splash.SplashViewModel
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class UnitTest: AutoCloseKoinTest() {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val splashViewModel: SplashViewModel by inject()
    private val loginViewModel: LoginViewModel by inject()

    @Before
    fun initKoin() {
        startKoin {
            modules(appModule)
        }
    }

}