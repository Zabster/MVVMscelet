package com.zabster.mvvmscelet.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val controller = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, controller)
    }
}
