package com.example.moviedbapplication.ui.fragment.login

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
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel<LoginEvent, LoginState, LoginEffect>() {

    override fun setInitialState(): LoginState = LoginState()

    override fun handleEvents(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClicked -> {
                loginUser(event.email, event.password)
            }

            is LoginEvent.OnSignUpClicked -> {
                setEffect { LoginEffect.NavigateToRegister }
            }

            is LoginEvent.OnResetPasswordClicked -> {
                resetPassword(event.email)
            }
        }
    }

    private fun loginUser(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            setEffect { LoginEffect.ShowFail("Lütfen tüm alanları doldurun") }
            return
        }

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    setState { copy(isLoading = false) }
                    if (task.isSuccessful) {
                        setEffect { LoginEffect.NavigateToHome }
                    } else {
                        setEffect {
                            LoginEffect.ShowFail(
                                task.exception?.localizedMessage ?: "Giriş başarısız"
                            )
                        }
                    }
                }
        }
    }

    private fun resetPassword(email: String) {
        if (email.isBlank()) {
            setEffect { LoginEffect.ShowFail("Lütfen e-posta adresi girin.") }
            return
        }

        viewModelScope.launch {
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    setEffect { LoginEffect.ShowSuccess("Şifre sıfırlama bağlantısı e-postanıza gönderildi.") }
                }
                .addOnFailureListener { exception ->
                    setEffect {
                        LoginEffect.ShowFail(
                            exception.localizedMessage
                                ?: "Şifre sıfırlama sırasında bir hata oluştu."
                        )
                    }
                }
        }
    }
}

sealed interface LoginEvent : Event {
    data class OnLoginClicked(
        val email: String,
        val password: String
    ) : LoginEvent

    data object OnSignUpClicked : LoginEvent
    data class OnResetPasswordClicked(val email: String) : LoginEvent
}

data class LoginState(
    val isLoading: Boolean = false
) : State

sealed interface LoginEffect : Effect {
    data object NavigateToHome : LoginEffect
    data object NavigateToRegister : LoginEffect
    data class ShowSuccess(val message: String) : LoginEffect
    data class ShowFail(val message: String) : LoginEffect
}

