package com.vcolofati.organizze.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vcolofati.organizze.databinding.ActivityIncomeBinding
import com.vcolofati.organizze.utils.DateHandler
import com.vcolofati.organizze.utils.Status
import com.vcolofati.organizze.viewmodels.IncomeViewModel

class IncomeActivity : AppCompatActivity() {

    private val viewModel: IncomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()

        binding.editDate.setText(DateHandler.currentDate())

        binding.fabSave.setOnClickListener {
            val value = binding.editValue.text.toString()
            val category = binding.editCategory.text.toString()
            val description = binding.editDescription.text.toString()
            val date = binding.editDate.text.toString()
            this.viewModel.saveIncome(value, category, description, date)
        }
    }

    private fun setObservers() {
        this.viewModel.feedback().observe(this) {
            when(it.status) {
                Status.SUCESS -> finish()
                Status.ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}