package com.zabster.mvvmscelet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zabster.mvvmscelet.ui.abstracts.BaseViewModel
import com.zabster.mvvmscelet.utils.SharedPreferenceHelper
import com.zabster.mvvmscelet.utils.SharedPreferenceTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class LoginViewModel : BaseViewModel(), KoinComponent {

    private val sharedPreferenceHelper: SharedPreferenceHelper by inject()

    private val _validateData = MutableLiveData<Boolean>()
    private val _loading = MutableLiveData<Boolean>()
    private val _messageForShowing = MutableLiveData<String>()

    val validateData: LiveData<Boolean>
        get() = _validateData
    val loading: LiveData<Boolean>
        get() = _loading
    val messageForShowing: LiveData<String>
        get() = _messageForShowing

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
        uiScope.launch {
            _loading.value = true
            try {
                delay(2000)
                setToken(email.plus(password))
                _validateData.value = true
            } catch (e: Exception) {
                _messageForShowing.value = "Error login" // todo hardcode
            } finally {
                _loading.value = false
            }
        }
    }

    private suspend fun setToken(token: String) {
        withContext(Dispatchers.Main) {
            sharedPreferenceHelper.putString(SharedPreferenceTag.USER_TOKEN.key, token)
        }
    }

    fun clearMessage() {
        _messageForShowing.value = ""
    }
}