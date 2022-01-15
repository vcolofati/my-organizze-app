package com.vcolofati.organizze.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vcolofati.organizze.models.Movement
import com.vcolofati.organizze.repositories.DatabaseRepository

class ExpensesViewModel(application: Application): AndroidViewModel(application) {

    private val repository = DatabaseRepository(application)

    fun saveExpense(value: Double, category: String, description: String, date: String) {
        val movement = Movement(date, category, description, "d" , value)
        this.repository.saveExpense(movement)

    }
}