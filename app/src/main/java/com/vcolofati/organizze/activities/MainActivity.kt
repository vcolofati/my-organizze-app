package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.vcolofati.organizze.R
import com.vcolofati.organizze.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fabExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
        }

        binding.fabIncome.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }
    }
}