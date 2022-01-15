package com.vcolofati.organizze.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vcolofati.organizze.databinding.ActivityExpensesBinding
import com.vcolofati.organizze.utils.DateHandler
import com.vcolofati.organizze.viewmodels.ExpensesViewModel

class ExpensesActivity : AppCompatActivity() {

    val viewModel: ExpensesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editDate.setText(DateHandler.currentDate())

        binding.fabSave.setOnClickListener {
            val value = binding.editValue.text.toString().toDouble()
            val category = binding.editCategory.text.toString()
            val description = binding.editDescription.text.toString()
            val date = binding.editDate.text.toString()
            this.viewModel.saveExpense(value, category, description, date)
        }
    }
}