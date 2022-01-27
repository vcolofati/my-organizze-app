package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.vcolofati.organizze.R
import com.vcolofati.organizze.activities.fragments.RedirectFragment
import com.vcolofati.organizze.viewmodels.CustomIntroViewModel

class CustomIntroActivity : IntroActivity() {

    private val viewModel: CustomIntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setObservers()

        setNavigationPolicy(
            object : NavigationPolicy {
                override fun canGoForward(position: Int): Boolean {
                    if (position == 4) {
                        return false
                    }
                    return true
                }

                override fun canGoBackward(position: Int): Boolean {
                    return true
                }
            }
        )

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
            .fragment(RedirectFragment.newInstance())
            .build())
    }

    override fun onStart() {
        super.onStart()
        this.isUserLogged()
    }

    private fun setObservers() {
        this.viewModel.isUserLogged().observe(this) {
            if(it == true) startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun isUserLogged() {
        this.viewModel.isUserLogged()
    }
}