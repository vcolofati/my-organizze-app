package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcolofati.organizze.models.Movimentation
import com.vcolofati.organizze.repositories.AuthRepository
import com.vcolofati.organizze.repositories.DatabaseRepository
import com.vcolofati.organizze.utils.Resource

class ExpensesViewModel(application: Application) : AndroidViewModel(application) {

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

    fun saveExpense(value: String, category: String, description: String, date: String) {
        if (this.validateFields(category, date, description, value)) {
            val doubleValue = value.toDouble()
            val movimentation = Movimentation(date, category, description, "d", doubleValue)
            this.mDatabaseRepository.saveMovimentation(movimentation)
            feedback.value = Resource.sucess(null)
            this.mDatabaseRepository.updateUserTotalExpenses(doubleValue)
        }
    }

    fun feedback(): LiveData<Resource<String>> {
        return feedback
    }
}