package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.repositories.AuthRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthRepository = AuthRepository(application)

    private val mIsUserLogged: MutableLiveData<Boolean> = MutableLiveData();

    fun isUserLogged() : LiveData<Boolean> {
        mIsUserLogged.value = this.repository.isUserLogged()
        return mIsUserLogged
    }

    fun signout() {
        this.repository.signout()
    }
}