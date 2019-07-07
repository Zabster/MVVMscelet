package com.zabster.mvvmscelet.ui.second

import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.databinding.FragmentSecondBinding
import com.zabster.mvvmscelet.ui.abstracts.BaseFragment

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    override fun getContentView(): Int = R.layout.fragment_second
    override fun init() {}
}