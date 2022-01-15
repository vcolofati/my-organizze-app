package com.vcolofati.organizze.repositories

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vcolofati.organizze.models.Movement
import com.vcolofati.organizze.models.User

class DatabaseRepository(private val application: Application) {
    companion object {
        private val database = Firebase.database
    }

    fun saveUserExtraData(userUuid: String, user: User) {
        database.getReference("users").child(userUuid).setValue(user)
    }

    fun saveExpense(movement: Movement) {

    }
}