package com.zabster.mvvmscelet.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class LoginViewModel : ViewModel(), KoinComponent {

    private val sharedPreferenceHelper: SharedPreferenceHelper by inject()

    private val disposable = CompositeDisposable()

    private val _validateData = MutableLiveData<Boolean>()
    private val _loading = MutableLiveData<Boolean>()
    private val _messageForShowing = MutableLiveData<String>()

    val validateData: LiveData<Boolean>
        get() = _validateData
    val loading: LiveData<Boolean>
        get() = _loading
    val messageForShowing: LiveData<String>
        get() = _messageForShowing

    // todo добавить шифрование для пароля

    fun initState() {
        _loading.value = false
    }

    fun tryLogin(email: String, password: String) {
        validate(email, password)
    }

    private fun validate(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            login(email, password)
        } else {
            _validateData.value = false
            _messageForShowing.value = "Invalid email or password" // todo hardcode
        }
    }

    private fun login(email: String, password: String) {
        _loading.value = true
        Observable.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: Long) {
                    setToken(email.plus(password))
                    _validateData.value = true
                    _loading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.e(toString(), "error login()", e)
                    _messageForShowing.value = "Error login" // todo hardcode
                    _loading.value = false
                }

            })
    }

    private fun setToken(token: String) {
        sharedPreferenceHelper.putString(SharedPreferenceTag.USER_TOKEN.key, token)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}