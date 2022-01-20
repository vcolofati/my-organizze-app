package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.models.Movement
import com.vcolofati.organizze.repositories.AuthRepository
import com.vcolofati.organizze.repositories.DatabaseRepository
import com.vcolofati.organizze.utils.Resource

class IncomeViewModel(application: Application): AndroidViewModel(application) {
    private val mDatabaseRepository = DatabaseRepository(application, AuthRepository.getUserUuid())

    private val feedback: MutableLiveData<Resource<String>> = MutableLiveData()

    private fun validateFields(category: String, date: String, description: String, value: String): Boolean {
        var valid = false
        when {
            category.isEmpty() -> feedback.value = Resource.error("Preencha a categoria", null)
            date.isEmpty() -> feedback.value = Resource.error("Preencha a data", null)
            description.isEmpty() -> feedback.value = Resource.error("Preencha a descrição", null)
            value.isEmpty() -> feedback.value = Resource.error("Preencha o valor", null)
            else -> valid = true
        }
        return valid
    }

    fun saveIncome(value: String, category: String, description: String, date: String) {
        if (this.validateFields(category, date, description, value)) {
            val doubleValue = value.toDouble()
            val movement = Movement(date, category, description, "i", doubleValue)
            this.mDatabaseRepository.saveMovement(movement)
            feedback.value = Resource.sucess(null)
            this.mDatabaseRepository.updateUserTotalIncome(doubleValue)
        }
    }

    fun feedback(): LiveData<Resource<String>> {
        return feedback
    }
}