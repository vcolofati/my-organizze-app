package com.vcolofati.organizze.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.repositories.AuthRepository

class SignupViewModel: ViewModel() {

    private val repository: AuthRepository = AuthRepository()

    private var mFeedback: MutableLiveData<String> = MutableLiveData()

    private fun validateFields(user: User): Boolean {
        var valid = false
        when {
            user.name.isEmpty() -> mFeedback.value = "Preencha o nome"
            user.email.isEmpty() -> mFeedback.value = "Preencha o email"
            user.password.isEmpty() -> mFeedback.value = "Preencha a senha"
            else -> valid = true
        }
        return valid
    }

    fun signup(user: User) : Task<AuthResult> {
        lateinit var task: Task<AuthResult>
        if (validateFields(user)) {
            task = this.repository.signup(user)
        }
        return task
    }

    fun feedback(): LiveData<String> {
        return mFeedback
    }
}