package com.zabster.mvvmscelet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.ui.base.MainActivity
import com.zabster.mvvmscelet.utils.setInvisability
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.initState()

        initObservers()
        initListeners()
    }

    private fun initListeners() {
        loginBtn.setOnClickListener {
            viewModel.tryLogin(emailEdit.text.toString(),
                passwordEdit.text.toString())
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer { isLoading: Boolean ->
            loadingView.setInvisability(isLoading)
            loginBtn.setInvisability(!isLoading)
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
