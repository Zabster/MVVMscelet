package com.zabster.mvvmscelet.di

import android.content.Context
import com.zabster.mvvmscelet.net.RestApi
import com.zabster.mvvmscelet.repos.SimpleRepository
import com.zabster.mvvmscelet.repos.SimpleRepositoryImpl
import com.zabster.mvvmscelet.ui.login.LoginViewModel
import com.zabster.mvvmscelet.ui.splash.SplashViewModel
import com.zabster.mvvmscelet.utils.SharedPreferenceHelper
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideCache(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }

    single<SimpleRepository> { SimpleRepositoryImpl(get()) }

    single { SharedPreferenceHelper(get()) }

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel() }
}

fun provideRetrofit(client: OkHttpClient): RestApi = Retrofit.Builder()
    .baseUrl("https://github.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(RestApi::class.java)

fun provideCache(applicationContext: Context): Cache {
    return Cache(applicationContext.cacheDir, 8 * 1024 * 1024 * 8)
}

fun provideOkHttpClient(cache: Cache): OkHttpClient {
    val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .addInterceptor(logger)
        .cache(cache)
        .build()
}