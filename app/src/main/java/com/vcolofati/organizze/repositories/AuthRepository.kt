package com.vcolofati.organizze.repositories

import android.app.Application
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.utils.SignupCallback

class AuthRepository(private val application: Application) {
    companion object {
        private val auth: FirebaseAuth = Firebase.auth
    }

    fun signup(user: User, callback: SignupCallback) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(application.mainExecutor) {
                when {
                    it.isSuccessful -> {
                        callback.onSignup(it.result!!.user!!.uid)
                    }
                    else -> {
                        // Tratar erros possíveis
                        var message = ""
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            message = "Digite uma senha mais forte"
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            message = "Por favor, digite um email válido"
                        } catch (e: FirebaseAuthUserCollisionException) {
                            message = "E-mail já está em uso"
                        } catch (e: Exception) {
                            message = "Erro ao cadastrar usuário: ${e.message}"
                        }
                        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun signin(user: User, callback: SignupCallback): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener(application.mainExecutor) {
            when {
                it.isSuccessful -> {
                    callback.onSignup(it.result!!.user!!.uid)
                }
                else -> {
                    var message = ""
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthInvalidUserException) {
                        message = "Usuário não existe"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        message = "Senha incorreta"
                    }  catch (e: Exception) {
                        message = "Erro ao logar usuário: ${e.message}"
                    }
                    Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun signout() {
        auth.signOut()
    }

    fun isUserLogged(): Boolean {
        if (auth.currentUser != null) {
            return true
        }
        return false
    }
}