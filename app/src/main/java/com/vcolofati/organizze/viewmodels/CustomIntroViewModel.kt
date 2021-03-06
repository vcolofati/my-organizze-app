package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.repositories.AuthRepository

class CustomIntroViewModel(application: Application): AndroidViewModel(application) {

    private val mIsUserLogged: MutableLiveData<Boolean> = MutableLiveData()

    private val repository = AuthRepository(application)

    fun isUserLogged() : LiveData<Boolean> {
        mIsUserLogged.value = this.repository.isUserLogged()
        return mIsUserLogged
    }
}