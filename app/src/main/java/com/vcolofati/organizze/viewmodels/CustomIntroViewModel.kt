package com.vcolofati.organizze.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vcolofati.organizze.repositories.AuthRepository

class CustomIntroViewModel: ViewModel() {

    private val mIsUserLogged: MutableLiveData<Boolean> = MutableLiveData()

    private val repository = AuthRepository()

    fun isUserLogged() : LiveData<Boolean> {
        mIsUserLogged.value = this.repository.isUserLogged()
        return mIsUserLogged
    }
}