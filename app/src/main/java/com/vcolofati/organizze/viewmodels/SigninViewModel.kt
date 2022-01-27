package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.repositories.AuthRepository
import com.vcolofati.organizze.utils.Resource

class SigninViewModel(application: Application) : AndroidViewModel(application) {

    private val mFeedback: MutableLiveData<Resource<String>> = MutableLiveData()

    private val repository = AuthRepository(application)

    private fun validateFields(user: User): Boolean {
        var valid = false
        when {
            user.email.isEmpty() -> mFeedback.value = Resource.error("Preencha o email", null)
            user.password.isEmpty() -> mFeedback.value = Resource.error("Preencha a senha", null)
            else -> valid = true
        }
        return valid
    }

    fun signin(email: String, password: String) {
        val user = User("", email, password)
        if (validateFields(user)) {
            this.repository.signin(user
            ) { mFeedback.value = Resource.sucess(null) }
        }
    }

    fun feedback(): LiveData<Resource<String>> {
        return mFeedback
    }
}