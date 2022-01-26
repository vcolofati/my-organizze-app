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
import com.vcolofati.organizze.R
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.utils.SignCallback

class AuthRepository(private val application: Application) {

    companion object {
        private val auth: FirebaseAuth = Firebase.auth

        fun getUserUuid(): String {
            return auth.currentUser!!.uid
        }
    }

    fun signup(user: User, callback: SignCallback) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(application.mainExecutor) {
                when {
                    it.isSuccessful -> {
                        callback.onSign(it.result!!.user!!.uid)
                    }
                    else -> {
                        // Tratar erros possíveis
                        var message = ""
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            message = application.getString(R.string.weak_password)
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            message = application.getString(R.string.invalid_credentials)
                        } catch (e: FirebaseAuthUserCollisionException) {
                            message = application.getString(R.string.user_collision)
                        } catch (e: Exception) {
                            message = application.getString(R.string.generic_signup_error, e.message)
                        }
                        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun signin(user: User, callback: SignCallback): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(application.mainExecutor) {
                when {
                    it.isSuccessful -> {
                        callback.onSign(it.result!!.user!!.uid)
                    }
                    else -> {
                        var message = ""
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            message = application.getString(R.string.invalid_user)
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            message = application.getString(R.string.credential_mismatch)
                        } catch (e: Exception) {
                            message = application.getString(R.string.generic_signin_error, e.message)
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