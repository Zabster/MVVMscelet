package com.zabster.mvvmscelet

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zabster.mvvmscelet.di.appModule
import com.zabster.mvvmscelet.ui.login.LoginViewModel
import com.zabster.mvvmscelet.ui.splash.SplashViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

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

    @Before
    fun initRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}