package com.zabster.mvvmscelet.ui.first

import com.zabster.mvvmscelet.R
import com.zabster.mvvmscelet.databinding.FragmentFirstBinding
import com.zabster.mvvmscelet.ui.abstracts.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstFragment : BaseFragment<FragmentFirstBinding>() {

    private val viewModel: FirstViewModel by viewModel()

    override fun getContentView(): Int = R.layout.fragment_first
    override fun init() {
        binding.viewModel = viewModel
    }
}
