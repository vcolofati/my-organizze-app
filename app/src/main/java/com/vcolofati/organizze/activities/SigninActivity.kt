package com.vcolofati.organizze.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vcolofati.organizze.databinding.ActivitySigninBinding
import com.vcolofati.organizze.utils.Status
import com.vcolofati.organizze.viewmodels.SigninViewModel

class SigninActivity : AppCompatActivity() {

    private val viewModel: SigninViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()

        binding.buttonSignin.setOnClickListener {
            val email = binding.editEmail2.text.toString()
            val password = binding.editPassword2.text.toString()
            this.viewModel.signin(email, password)
        }
    }

    fun setObservers() {
        this.viewModel.feedback().observe(this) {
            when (it.status) {
                Status.SUCESS -> finish()
                Status.ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}