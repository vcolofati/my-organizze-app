package com.vcolofati.organizze.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.vcolofati.organizze.models.Movimentation
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.utils.DateHandler

class DatabaseRepository(
    private val application: Application,
    userUuid: String,
    val userData: MutableLiveData<User>? = null,
    val movimentationData: MutableLiveData<List<Movimentation>>? = null
) {

    companion object {
        private val database = Firebase.database
    }

    private lateinit var tempDate: String
    private val userRef = database.getReference("users").child(userUuid)
    private val movimentRef = database.getReference("movimentation").child(userUuid)

    private var value: User? = null
    private var movList: MutableList<Movimentation> = ArrayList()

    private val userValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            userData?.value = snapshot.getValue<User>()
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    private val movimentationValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            movList.clear()
            for (movimentationSnapshot: DataSnapshot in snapshot.children) {
                val movimentation = movimentationSnapshot.getValue<Movimentation>()
                movimentation?.let {
                    movimentation.key = movimentationSnapshot.key
                    movList.add(movimentation)
                }
            }
            movimentationData?.value = movList
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    fun saveUserExtraData(uuid: String, user: User) {
        this.userRef.child(uuid).setValue(user)
    }

    fun setUserDataListener() {
        this.userRef.addValueEventListener(userValueEventListener)
    }

    fun detachUserDataListener() {
        this.userRef.removeEventListener(userValueEventListener)
    }

    fun detachMovimentationDataListener() {
        this.movimentRef.child(tempDate).removeEventListener(movimentationValueEventListener)
    }

    fun saveMovimentation(movimentation: Movimentation) {
        movimentRef
            .child(DateHandler.removeInvalidCharacters(movimentation.date))
            .push()
            .setValue(movimentation)
    }

    fun updateUserTotalExpenses(value: Double) {
        this.userRef.get().addOnSuccessListener {
            val user = it.getValue<User>()
            this.userRef.child("totalExpenses").setValue(user?.totalExpenses?.plus(value))
        }.addOnFailureListener { }
    }

    fun updateUserTotalIncome(value: Double) {

        this.userRef.get().addOnSuccessListener {
            val user = it.getValue<User>()
            this.userRef.child("totalIncome").setValue(user?.totalIncome?.plus(value))
        }.addOnFailureListener { }
    }

    fun getMovimentations(date: String) {
        this.tempDate = date
        movimentRef
            .child(date).addValueEventListener(movimentationValueEventListener)
    }

    fun removeMovimentation(key: String) {
        movimentRef
            .child(tempDate)
            .child(key)
            .removeValue()
    }
}