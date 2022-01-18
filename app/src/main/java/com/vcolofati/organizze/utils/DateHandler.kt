package com.vcolofati.organizze.utils

import java.text.SimpleDateFormat

class DateHandler {
    companion object {
        private val sdf = SimpleDateFormat("dd/MM/yyyy")

        fun currentDate(): String {
            val date = System.currentTimeMillis()
            return sdf.format(date)
        }

        fun removeInvalidCharacters(date: String): String {
            val formattedDate = date.split("/")
            return formattedDate[1] + formattedDate[2]
        }
    }
}