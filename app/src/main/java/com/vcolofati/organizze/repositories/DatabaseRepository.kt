package com.vcolofati.organizze.repositories

import android.app.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.vcolofati.organizze.models.Movement
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.utils.DateHandler

class DatabaseRepository(private val application: Application) {

    companion object {
        private val database = Firebase.database
    }

    fun saveUserExtraData(userUuid: String, user: User) {
        database.getReference("users").child(userUuid).setValue(user)
    }

    fun saveMovement(uuid: String, movement: Movement) {
        database.getReference("movimentation")
            .child(uuid)
            .child(DateHandler.removeInvalidCharacters(movement.date))
            .push()
            .setValue(movement)
    }

    fun updateUserTotalExpenses(uuid: String, value: Double) {
        val databaseRef = database.getReference("users").child(uuid)
        databaseRef.get().addOnSuccessListener {
            val user = it.getValue<User>()
            databaseRef.child("totalExpenses").setValue(user!!.totalExpenses + value)
        }.addOnFailureListener { }
    }

    fun updateUserTotalIncome(uuid: String, value: Double) {
        val databaseRef = database.getReference("users").child(uuid)
        databaseRef.get().addOnSuccessListener {
            val user = it.getValue<User>()
            databaseRef.child("totalIncome").setValue(user!!.totalIncome + value)
        }.addOnFailureListener { }
    }
}