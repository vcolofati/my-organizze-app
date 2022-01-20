package com.vcolofati.organizze.utils

import com.vcolofati.organizze.models.User
import java.text.NumberFormat
import java.util.Locale

class MainScreenUtil {
    companion object {

        private val numberFormat = NumberFormat
            .getCurrencyInstance(Locale("pt", "BR"))

        val formatName = { user: User -> "Olá, ${user.name}"}

        val formatBalance = { user: User -> numberFormat
            .format((user.totalIncome - user.totalExpenses))}
    }
}