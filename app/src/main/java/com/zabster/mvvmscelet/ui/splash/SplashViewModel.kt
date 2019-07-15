package com.zabster.mvvmscelet.ui.splash

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zabster.mvvmscelet.db.AppDatabase
import com.zabster.mvvmscelet.db.entities.SomeDBModel
import com.zabster.mvvmscelet.repos.SimpleRepository
import com.zabster.mvvmscelet.ui.abstracts.BaseViewModel
import com.zabster.mvvmscelet.utils.SharedPreferenceHelper
import com.zabster.mvvmscelet.utils.SharedPreferenceTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class SplashViewModel(
    private val repository: SimpleRepository,
    private val db: AppDatabase
) : BaseViewModel(), KoinComponent {

    private val sharedPreferenceHelper: SharedPreferenceHelper by inject()

    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Boolean>()
    private val _loginState = MutableLiveData<Boolean>()

    val loading: LiveData<Int> = Transformations.map(_loading) { isLoading ->
        if (isLoading) View.VISIBLE
        else View.INVISIBLE
    }
    val error: LiveData<Int> = Transformations.map(_error) { isLoading ->
        if (isLoading) View.VISIBLE
        else View.INVISIBLE
    }
    val loginState: LiveData<Boolean>
        get() = _loginState

    init {
        _error.value = false
        _loading.value = false
    }

    fun sync() {
        uiScope.launch {
            _loading.value = true
            try {
                syncSomeData()
                checkLogin()
                setToDb()
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    private suspend fun syncSomeData() {
        withContext(Dispatchers.IO) {
            repository.syncSomeData()
        }
    }

    private suspend fun setToDb() {
        withContext(Dispatchers.IO) {
            val date = System.currentTimeMillis()
            db.someDao().insert(SomeDBModel(name = date.toString(), date = date))
        }
    }

    private fun checkLogin() {
        _loginState.value = sharedPreferenceHelper
            .getString(SharedPreferenceTag.USER_TOKEN.key).isNotEmpty()
    }
}