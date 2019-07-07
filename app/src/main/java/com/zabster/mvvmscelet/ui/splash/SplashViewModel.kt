package com.zabster.mvvmscelet.ui.splash

import androidx.lifecycle.LiveData
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

    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Boolean>()
    private val _loginState = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading
    val error: LiveData<Boolean>
        get() = _error
    val loginState: LiveData<Boolean>
        get() = _loginState

    fun sync() {
        syncSomeData()
    }

    private fun syncSomeData() {
        _loading.value = true
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: Long) {
                    _error.value = false
                    _loading.value = false
                    checkLogin()
                }

                override fun onError(e: Throwable) {
                    _loading.value = false
                    _error.value = true
                }
            })

    }

    private fun checkLogin() {
        _loginState.value = sharedPreferenceHelper
            .getString(SharedPreferenceTag.USER_TOKEN.key).isNotEmpty()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}