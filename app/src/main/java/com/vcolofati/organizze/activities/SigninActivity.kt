package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.vcolofati.organizze.R
import com.vcolofati.organizze.viewmodels.SigninViewModel

class SigninActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var buttonSignin: Button
    private val viewModel: SigninViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        editEmail = findViewById(R.id.editEmail2)
        editPassword = findViewById(R.id.editPassword2)
        buttonSignin = findViewById(R.id.buttonSignin)

        setObservers()

        buttonSignin.setOnClickListener {
            val email = this.editEmail.text.toString()
            val password = this.editPassword.text.toString()
            this.viewModel.signin(email, password)
        }
    }

    fun setObservers() {
        this.viewModel.feedback().observe(this, { feedback: String ->
            Toast.makeText(this, feedback, Toast.LENGTH_SHORT).show()
        })

        this.viewModel.task().observe(this, { task ->
            task.addOnCompleteListener(this) {
                when {
                    task.isSuccessful -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else -> {
                        var message = ""
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            message = "Usuário não existe"
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            message = "Senha incorreta"
                        }  catch (e: Exception) {
                            message = "Erro ao logar usuário: ${e.message}"
                        }
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}