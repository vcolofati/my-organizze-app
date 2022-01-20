package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.repositories.AuthRepository
import com.vcolofati.organizze.repositories.DatabaseRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mIsUserLogged: MutableLiveData<Boolean> = MutableLiveData()
    private val userData: MutableLiveData<User> = MutableLiveData()

    private val mAuthRepository: AuthRepository = AuthRepository(application)
    private val mDatabaseRepository: DatabaseRepository = DatabaseRepository(application,
            AuthRepository.getUserUuid(), userData)

    fun isUserLogged() : LiveData<Boolean> {
        mIsUserLogged.value = this.mAuthRepository.isUserLogged()
        return mIsUserLogged
    }

    fun signout() {
        this.mAuthRepository.signout()
    }

    fun userData(): LiveData<User> {
        return userData
    }

    fun setUserDataListener() {
        this.mDatabaseRepository.userDataListener()
    }

    fun detachDataListener() {
        this.mDatabaseRepository.detachListener()
    }
}