package com.zabster.mvvmscelet.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.zabster.mvvmscelet.db.AppDatabase
import com.zabster.mvvmscelet.net.RestApi
import com.zabster.mvvmscelet.repos.SimpleRepository
import com.zabster.mvvmscelet.repos.SimpleRepositoryImpl
import com.zabster.mvvmscelet.ui.first.FirstViewModel
import com.zabster.mvvmscelet.ui.login.LoginViewModel
import com.zabster.mvvmscelet.ui.splash.SplashViewModel
import com.zabster.mvvmscelet.utils.SharedPreferenceHelper
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRoomDatabase(get(), listOf()) }
    single { provideCache(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }

    single<SimpleRepository> { SimpleRepositoryImpl(get()) }

    single { SharedPreferenceHelper(get()) }

    viewModel { SplashViewModel(get(), get()) }
    viewModel { LoginViewModel() }
    viewModel { FirstViewModel(get()) }
}

fun provideRetrofit(client: OkHttpClient): RestApi = Retrofit.Builder()
    .baseUrl("https://github.com/")
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

fun provideRoomDatabase(applicationContext: Context, listMigrations: List<Migration>): AppDatabase =
    Room.databaseBuilder(applicationContext, AppDatabase::class.java, "AppStorage")
        .addMigrations(*listMigrations.toTypedArray())
        .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
        .build()