package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vcolofati.organizze.R
import com.vcolofati.organizze.databinding.ActivityMainBinding
import com.vcolofati.organizze.utils.MainScreenUtil
import com.vcolofati.organizze.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setObservers()
        configureCalendarView()

        binding.fabExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
        }

        binding.fabIncome.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        this.viewModel.setUserDataListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> {
                this.viewModel.signout()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        this.viewModel.isUserLogged().observe(this) {
            if (it == false) {
                finish()
            }
        }
        this.viewModel.userData().observe(this) {
            binding.mainContent.textGreeting.text = MainScreenUtil.formatName(it)
            binding.mainContent.textBalance.text = MainScreenUtil.formatBalance(it)
        }
    }

    private fun configureCalendarView() {
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
        binding.mainContent.calendarView.setTitleMonths(months)
    }

    override fun onStop() {
        this.viewModel.detachDataListener()
        super.onStop()
    }
}