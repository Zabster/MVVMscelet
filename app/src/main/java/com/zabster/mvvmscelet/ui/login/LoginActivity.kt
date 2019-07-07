package com.zabster.mvvmscelet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.databinding.ActivityLoginBinding
import com.zabster.mvvmscelet.ui.base.MainActivity
import com.zabster.mvvmscelet.utils.setInvisability
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.initState()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.loginBtn.setOnClickListener {
            viewModel.tryLogin(binding.emailEdit.text.toString(),
                binding.passwordEdit.text.toString())
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer { isLoading: Boolean ->
            binding.loadingView.setInvisability(isLoading)
            binding.loginBtn.setInvisability(!isLoading)
        })

        viewModel.messageForShowing.observe(this, Observer { message: String ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                viewModel.clearMessage()
            }
        })

        viewModel.validateData.observe(this, Observer { isValid: Boolean ->
            if (isValid) {
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(mainIntent)
            }
        })
    }
}
