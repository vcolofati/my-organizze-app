package com.vcolofati.organizze.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.vcolofati.organizze.models.Movement
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.utils.DateHandler

class DatabaseRepository(
    private val application: Application,
    private val userUuid: String,
    val data: MutableLiveData<User>? = null
) {

    companion object {
        private val database = Firebase.database
    }

    val userRef = database.getReference("users").child(userUuid)

    private var value: User? = null

    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            data?.value = snapshot.getValue<User>()
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    fun saveUserExtraData(uuid: String, user: User) {
        this.userRef.child(uuid).setValue(user)
    }

    fun userDataListener() {
        this.userRef.addValueEventListener(valueEventListener)
    }

    fun detachListener() {
        this.userRef.removeEventListener(valueEventListener)
    }

    fun saveMovement(movement: Movement) {
        database.getReference("movimentation")
            .child(userUuid)
            .child(DateHandler.removeInvalidCharacters(movement.date))
            .push()
            .setValue(movement)
    }

    fun updateUserTotalExpenses(value: Double) {
        this.userRef.get().addOnSuccessListener {
            val user = it.getValue<User>()
            this.userRef.child("totalExpenses").setValue(user!!.totalExpenses + value)
        }.addOnFailureListener { }
    }

    fun updateUserTotalIncome(value: Double) {

        this.userRef.get().addOnSuccessListener {
            val user = it.getValue<User>()
            this.userRef.child("totalIncome").setValue(user!!.totalIncome + value)
        }.addOnFailureListener { }
    }
}