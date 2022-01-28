package com.vcolofati.organizze.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heinrichreimersoftware.materialintro.app.SlideFragment
import com.vcolofati.organizze.activities.SigninActivity
import com.vcolofati.organizze.activities.SignupActivity
import com.vcolofati.organizze.databinding.FragmentRedirectBinding

class RedirectFragment : SlideFragment() {

    private var _binding: FragmentRedirectBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRedirectBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.textRedirectSignin.setOnClickListener {
            startActivity(Intent(activity, SigninActivity::class.java))
        }
        binding.buttonRedirectSignup.setOnClickListener {
            startActivity(Intent(activity, SignupActivity::class.java))
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): RedirectFragment {
            return RedirectFragment()
        }
    }
}