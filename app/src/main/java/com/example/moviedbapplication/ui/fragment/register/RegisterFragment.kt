package com.example.moviedbapplication.ui.fragment.register

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
import com.example.moviedbapplication.common.extensions.setupKeyboardHidingOnTouch
import com.example.moviedbapplication.common.extensions.togglePasswordVisibility
import com.example.moviedbapplication.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterEvent, RegisterState, RegisterEffect>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater)
    }


    override fun bindScreen() {

        setupKeyboardHidingOnTouch(requireView())
        setupPasswordVisibilityToggle()

        binding.apply {
            buttonRegister.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()
                val confirmPassword = editTextConfirmPassword.text.toString()

                viewModel.setEvent(
                    RegisterEvent.OnRegisterClicked(
                        name = name,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                )
            }

            textViewContinueWithLogin.setOnClickListener {
                viewModel.setEvent(RegisterEvent.OnSignInClicked)
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
                is RegisterEffect.NavigateToHome -> {
                    findNavController().navigateSafe(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                }

                is RegisterEffect.NavigateToSignIn -> {
                    findNavController().navigateSafe(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                }

                is RegisterEffect.ShowFail -> {
                    Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        viewBindingScope {
            editTextPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
            editTextConfirmPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
        }
    }

    private fun clearEditTextFields() {
        viewBindingScope {
            editTextName.text.clear()
            editTextEmail.text.clear()
            editTextPassword.text.clear()
            editTextConfirmPassword.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        clearEditTextFields()
    }

}