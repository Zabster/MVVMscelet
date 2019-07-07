package com.zabster.mvvmscelet.ui.first

import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.databinding.FragmentFirstBinding
import com.zabster.mvvmscelet.ui.abstracts.BaseFragment

class FirstFragment : BaseFragment<FragmentFirstBinding>() {
    override fun getContentView(): Int = R.layout.fragment_first
    override fun init() {}
}
