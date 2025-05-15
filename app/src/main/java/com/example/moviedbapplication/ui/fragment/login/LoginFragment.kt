package com.example.moviedbapplication.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviedbapplication.common.base.BaseFragment
import com.example.moviedbapplication.common.extensions.collectIn
import com.example.moviedbapplication.common.extensions.navigateSafe
import com.example.moviedbapplication.common.extensions.setUpBottomSheetDialog
import com.example.moviedbapplication.common.extensions.setupKeyboardHidingOnTouch
import com.example.moviedbapplication.common.extensions.togglePasswordVisibility
import com.example.moviedbapplication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginEvent, LoginState, LoginEffect>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater)
    }


    override fun bindScreen() {

        setupKeyboardHidingOnTouch(requireView())
        setupPasswordVisibilityToggle()

        binding.apply {
            buttonLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()

                viewModel.setEvent(
                    LoginEvent.OnLoginClicked(
                        email = email,
                        password = password
                    )
                )
            }

            textViewContinueWithRegister.setOnClickListener {
                viewModel.setEvent(LoginEvent.OnSignUpClicked)
            }

            textViewForgotPassword.setOnClickListener {
                val email = editTextEmail.text.toString()
                setUpBottomSheetDialog {
                    viewModel.setEvent(LoginEvent.OnResetPasswordClicked(email = email))
                }
            }
        }
        collectState()
        collectEffect()
    }

    private fun collectState() {
        viewModel.state.collectIn(viewLifecycleOwner) { state ->
            //Todo
        }
    }

    private fun collectEffect() {
        viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome -> {
                    findNavController().navigateSafe(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }

                is LoginEffect.NavigateToRegister -> {
                    findNavController().navigateSafe(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }

                is LoginEffect.ShowSuccess -> {
                    Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                }

                is LoginEffect.ShowFail -> {
                    Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        binding.editTextPassword.apply {
            setOnClickListener {
                togglePasswordVisibility()
            }
        }
    }

    private fun clearEditTextFields() {
        viewBindingScope {
            editTextEmail.text?.clear()
            editTextPassword.text?.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        clearEditTextFields()
    }
}