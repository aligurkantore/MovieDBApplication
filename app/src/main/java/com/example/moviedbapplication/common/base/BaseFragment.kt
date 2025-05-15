package com.example.moviedbapplication.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding, Event, State, Effect> : Fragment() {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: BaseViewModel<Event, State, Effect>
    protected abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindScreen()
    }

    protected inline fun viewModelScope(action: BaseViewModel<Event, State, Effect>.() -> Unit) {
        action(viewModel)
    }

    protected inline fun viewBindingScope(action: Binding.() -> Unit) {
        action(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun bindScreen()

}