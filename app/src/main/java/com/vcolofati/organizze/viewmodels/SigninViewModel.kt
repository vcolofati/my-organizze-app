package com.vcolofati.organizze.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.repositories.AuthRepository

class SigninViewModel : ViewModel() {

    private val mFeedback: MutableLiveData<String> = MutableLiveData()
    private val mTask: MutableLiveData<Task<AuthResult>> = MutableLiveData()

    private val repository = AuthRepository()

    private fun validateFields(user: User): Boolean {
        var valid = false
        when {
            user.email.isEmpty() -> mFeedback.value = "Preencha o email"
            user.password.isEmpty() -> mFeedback.value = "Preencha a senha"
            else -> valid = true
        }
        return valid
    }

    fun signin(email: String, password: String) {
        val user = User("", email, password)
        if (validateFields(user)) {
            mTask.value = this.repository.signin(user)
        }
    }

    fun feedback(): LiveData<String> {
        return mFeedback
    }

    fun task(): LiveData<Task<AuthResult>> {
        return mTask
    }
}