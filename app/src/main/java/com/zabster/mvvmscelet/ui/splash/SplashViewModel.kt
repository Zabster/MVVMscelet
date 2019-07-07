package com.zabster.mvvmscelet.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zabster.mvvmscelet.repos.SimpleRepository
import com.zabster.mvvmscelet.utils.SharedPreferenceHelper
import com.zabster.mvvmscelet.utils.SharedPreferenceTag
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class SplashViewModel(repository: SimpleRepository) : ViewModel(), KoinComponent {

    private val sharedPreferenceHelper: SharedPreferenceHelper by inject()

    private val disposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val loginState = MutableLiveData<Boolean>()

    fun sync() {
        syncSomeData()
    }

    private fun syncSomeData() {
        loading.value = true
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: Long) {
                    error.value = false
                    checkLogin()
                }

                override fun onError(e: Throwable) {
                    loading.value = false
                    error.value = true
                }
            })

    }

    private fun checkLogin() {
        loginState.value = sharedPreferenceHelper
            .getString(SharedPreferenceTag.USER_TOKEN.key).isNotEmpty()
    }
}