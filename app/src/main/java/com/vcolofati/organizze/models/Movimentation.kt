package com.vcolofati.organizze.models

class Movimentation(val date: String = "", val category: String = "",
                    val description: String = "", val type: String = "",
                    val value: Double = 0.0, var key: String? = null)