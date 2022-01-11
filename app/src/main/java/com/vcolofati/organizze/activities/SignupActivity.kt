package com.vcolofati.organizze.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.vcolofati.organizze.R
import com.vcolofati.organizze.models.User
import com.vcolofati.organizze.viewmodels.SignupViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var buttonSignup: Button
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        buttonSignup = findViewById(R.id.buttonSignup)
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        setObservers()

        buttonSignup.setOnClickListener {
            val name = this.editName.text.toString()
            val email = this.editEmail.text.toString()
            val password = this.editPassword.text.toString()
            this.viewModel.signup(User(name, email, password)).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sucesso ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                } else {
                    var message = ""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        message = "Digite uma senha mais forte"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        message = "Por favor, digite um email válido"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        message = "E-mail já está em uso"
                    } catch (e: Exception) {
                        message = "Erro ao cadastrar usuário: ${e.message}"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setObservers() {
        this.viewModel.feedback().observe(this, { feedback: String ->
            Toast.makeText(this, feedback, Toast.LENGTH_SHORT).show()
        })
    }
}