package com.example.moviedbapplication.ui.fragment.register

import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.common.base.BaseViewModel
import com.example.moviedbapplication.common.base.Effect
import com.example.moviedbapplication.common.base.Event
import com.example.moviedbapplication.common.base.State
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<RegisterEvent, RegisterState, RegisterEffect>() {

    override fun setInitialState(): RegisterState = RegisterState()

    override fun handleEvents(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnRegisterClicked -> {
                registerUser(
                    name = event.name,
                    email = event.email,
                    password = event.password,
                    confirmPassword = event.confirmPassword
                )
            }

            is RegisterEvent.OnSignInClicked -> {
                setEffect { RegisterEffect.NavigateToSignIn }
            }
        }
    }

    private fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            setEffect { RegisterEffect.ShowFail("Lütfen tüm alanları doldurun") }
            return
        }

        if (password != confirmPassword) {
            setEffect { RegisterEffect.ShowFail("Şifreler uyuşmuyor") }
            return
        }

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setEffect { RegisterEffect.NavigateToHome }
                    } else {
                        setEffect {
                            RegisterEffect.ShowFail(
                                task.exception?.message ?: "Kayıt başarısız"
                            )
                        }
                    }
                }
        }
    }
}


sealed interface RegisterEvent : Event {
    data class OnRegisterClicked(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : RegisterEvent

    data object OnSignInClicked : RegisterEvent
}

data class RegisterState(
    val isLoading: Boolean = false
) : State

sealed interface RegisterEffect : Effect {
    data object NavigateToHome : RegisterEffect
    data object NavigateToSignIn : RegisterEffect
    data class ShowFail(val message: String) : RegisterEffect
}

