package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.vcolofati.organizze.R
import com.vcolofati.organizze.viewmodels.CustomIntroViewModel

class CustomIntroActivity : IntroActivity() {

    private val viewModel: CustomIntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setObservers()

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_1)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_2)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_3)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_4)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .canGoForward(false)
            .fragment(R.layout.intro_signup_redirect)
            .build())
    }

    override fun onResume() {
        super.onResume()
        this.isUserLogged()
    }

    private fun setObservers() {
        this.viewModel.isUserLogged().observe(this, {
            if(it == true) startActivity(Intent(this, MainActivity::class.java))
        })
    }

    private fun isUserLogged() {
        this.viewModel.isUserLogged()
    }

    fun btnSignup(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun btnSignin(view: View) {
        startActivity(Intent(this, SigninActivity::class.java))
    }
}