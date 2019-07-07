package com.zabster.mvvmscelet.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.databinding.ActivitySplashBinding
import com.zabster.mvvmscelet.ui.base.MainActivity
import com.zabster.mvvmscelet.ui.login.LoginActivity
import com.zabster.mvvmscelet.utils.setVisability
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.sync()
        initObservers()
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer { isLoading: Boolean ->
            binding.progressView.setVisability(isLoading)
        })

        viewModel.error.observe(this, Observer { isError: Boolean ->
            binding.errorText.setVisability(isError)
        })

        viewModel.loginState.observe(this, Observer { isLogin: Boolean ->
            if (isLogin) {
                val baseIntent = Intent(this, MainActivity::class.java)
                baseIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(baseIntent)
            } else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)
            }
        })
    }
}
