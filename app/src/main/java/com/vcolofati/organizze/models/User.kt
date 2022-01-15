package com.vcolofati.organizze.models

import com.google.firebase.database.Exclude

class User ( val name: String, var email: String, @Exclude @get:Exclude @set:Exclude var password: String) {
    @Exclude val uuid: String? = null
    val totalExpenses = 0.0
    val totalIncome = 0.0
}