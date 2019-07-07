package com.zabster.mvvmscelet.ui.login

import android.util.Log
import android.widget.Toast
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

    val validateData = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val messageForShowing = MutableLiveData<String>()

    // todo добавить шифрование для пароля

    fun initState() {
        loading.value = false
    }

    fun tryLogin(email: String, password: String) {
        validate(email, password)
    }

    private fun validate(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            login(email, password)
        } else {
            validateData.value = false
            messageForShowing.value = "Invalid email or password" // todo hardcode
        }
    }

    private fun login(email: String, password: String) {
        loading.value = true
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
                    validateData.value = true
                    loading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.e(toString(), "error login()", e)
                    messageForShowing.value = "Error login" // todo hardcode
                    loading.value = false
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