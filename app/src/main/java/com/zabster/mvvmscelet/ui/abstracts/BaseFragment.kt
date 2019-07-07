package com.zabster.mvvmscelet.ui.abstracts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var binding: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getContentView(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    protected fun navigate(id: Int) {
        view?.findNavController()?.navigate(id)
    }

    protected fun pop() {
        view?.findNavController()?.popBackStack()
    }

    abstract fun getContentView(): Int
    abstract fun init()
}