package com.vcolofati.organizze.models

import com.google.firebase.database.Exclude

class User ( val name: String = "", var email: String = "", @get:Exclude var password: String = "") {
    var totalExpenses = 0.0
    var totalIncome = 0.0
}