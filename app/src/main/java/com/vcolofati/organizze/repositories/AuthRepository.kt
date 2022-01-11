package com.vcolofati.organizze.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vcolofati.organizze.models.User

class AuthRepository {
    companion object {
        private val auth: FirebaseAuth = Firebase.auth
    }

    fun signup(user: User) : Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(user.email, user.password)
    }
}