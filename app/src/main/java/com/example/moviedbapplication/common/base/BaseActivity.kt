package com.example.moviedbapplication.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM: ViewModel> : HiltActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private lateinit var viewModel: VM

    abstract fun getViewBinding(): VB
    abstract fun getViewModelClass(): Class<VM>
    abstract fun setupUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        setContentView(binding.root)
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}