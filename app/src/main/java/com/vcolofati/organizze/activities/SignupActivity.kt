package com.vcolofati.organizze.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vcolofati.organizze.databinding.ActivitySignupBinding
import com.vcolofati.organizze.utils.Status
import com.vcolofati.organizze.viewmodels.SignupViewModel

class SignupActivity : AppCompatActivity() {

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setObservers()

        binding.buttonSignup.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            this.viewModel.signup(name, email, password)
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