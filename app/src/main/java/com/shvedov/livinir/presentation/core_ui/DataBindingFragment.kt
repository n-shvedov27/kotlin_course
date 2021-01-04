package com.shvedov.livinir.presentation.core_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class DataBindingFragment<VM: ViewModel, B: ViewDataBinding> : BaseFragment<VM>() {

    abstract var binding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }
}