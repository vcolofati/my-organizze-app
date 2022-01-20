package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.repositories.AuthRepository
import com.vcolofati.organizze.repositories.DatabaseRepository
import com.vcolofati.organizze.utils.Resource
import com.vcolofati.organizze.utils.SignCallback

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val mFeedback: MutableLiveData<Resource<String>> = MutableLiveData()

    private val mAuthRepository: AuthRepository = AuthRepository(application)
    private val mDatabaseRepository: DatabaseRepository = DatabaseRepository(application, AuthRepository.getUserUuid())

    private fun validateFields(user: User): Boolean {
        var valid = false
        when {
            user.name.isEmpty() -> mFeedback.value = Resource.error("Preencha o nome", null)
            user.email.isEmpty() -> mFeedback.value = Resource.error("Preencha o email", null)
            user.password.isEmpty() -> mFeedback.value = Resource.error("Preencha a senha", null)
            else -> valid = true
        }
        return valid
    }

    fun signup(name: String, email: String, password: String) {
        val user = User(name, email, password)
        if (validateFields(user)) {
           this.mAuthRepository.signup(user) { uuid ->
               mFeedback.value = Resource.sucess(null)
               saveUserExtraData(uuid, user)
           }
        }
    }

    fun feedback(): LiveData<Resource<String>> {
        return mFeedback
    }

    private fun saveUserExtraData(uuid: String, user: User) {
        this.mDatabaseRepository.saveUserExtraData(uuid, user)
    }
}