package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.models.Movimentation
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.repositories.AuthRepository
import com.vcolofati.organizze.repositories.DatabaseRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mIsUserLogged: MutableLiveData<Boolean> = MutableLiveData()
    private val userData: MutableLiveData<User> = MutableLiveData()
    private val movimentationData: MutableLiveData<List<Movimentation>> = MutableLiveData()

    private val mAuthRepository: AuthRepository = AuthRepository(application)
    private val mDatabaseRepository: DatabaseRepository = DatabaseRepository(application,
            AuthRepository.getUserUuid(), userData,  movimentationData)

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

    fun movimentationData(): LiveData<List<Movimentation>> {
        return movimentationData
    }

    fun setListeners() {
        this.mDatabaseRepository.setUserDataListener()
    }

    fun getMovimentations(date: String) {
        this.mDatabaseRepository.getMovimentations(date)
    }

    fun detachUserDataListener() {
        this.mDatabaseRepository.detachUserDataListener()
    }

    fun detachMovimentationDataListener() {
        this.mDatabaseRepository.detachMovimentationDataListener()
    }

    fun removeMovimentation(key: String) {
        this.mDatabaseRepository.removeMovimentation(key)
    }

    fun updateUserIncome(value: Double, type: String) {
        if (type == "d") {
           this.mDatabaseRepository.updateUserTotalExpenses(value)
        } else {
            this.mDatabaseRepository.updateUserTotalIncome(value)

        }
    }
}