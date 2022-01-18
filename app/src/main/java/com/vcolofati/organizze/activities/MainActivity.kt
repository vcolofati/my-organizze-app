package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vcolofati.organizze.R
import com.vcolofati.organizze.databinding.ActivityMainBinding
import com.vcolofati.organizze.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fabExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
        }

        binding.fabIncome.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }

        setObservers()

        val buttonSair: Button = findViewById(R.id.buttonSair)

        buttonSair.setOnClickListener {
            this.viewModel.signout()
            finish()
        }
    }

    private fun setObservers() {
        this.viewModel.isUserLogged().observe(this) {
            if (it == false) {
                finish()
            }
        }
    }
}