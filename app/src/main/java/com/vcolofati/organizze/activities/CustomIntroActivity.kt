package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.vcolofati.organizze.R

class CustomIntroActivity : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(FragmentSlide.Builder()
            .background(R.color.white)
            .fragment(R.layout.intro_1)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(R.color.white)
            .fragment(R.layout.intro_2)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(R.color.white)
            .fragment(R.layout.intro_3)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(R.color.white)
            .fragment(R.layout.intro_4)
            .build())

        addSlide(FragmentSlide.Builder()
            .background(R.color.white)
            .canGoForward(false)
            .fragment(R.layout.intro_signup_redirect)
            .build())
    }

    fun btnSignup(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun btnSignin(view: View) {
        startActivity(Intent(this, SigninActivity::class.java))
    }
}