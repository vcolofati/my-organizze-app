package com.vcolofati.organizze.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vcolofati.organizze.R
import com.vcolofati.organizze.adapters.MovimentationAdapter
import com.vcolofati.organizze.databinding.ActivityMainBinding
import com.vcolofati.organizze.utils.MainScreenUtil
import com.vcolofati.organizze.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: MovimentationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fabExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
            binding.fabMenu.close(false)
        }

        binding.fabIncome.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
            binding.fabMenu.close(false)
        }

        binding.mainContent.calendarView.setOnMonthChangedListener { _, date ->
            this.viewModel.detachMovimentationDataListener()
            val monthYear = "%02d".format(date.month) + date.year
            this.viewModel.getMovimentations(monthYear)
        }

        // Layout Manager
        binding.mainContent.recyclerMovimentations.layoutManager = LinearLayoutManager(this)
        binding.mainContent.recyclerMovimentations.setHasFixedSize(true)
        // Adapter
        this.mAdapter = MovimentationAdapter(this)
        binding.mainContent.recyclerMovimentations.adapter = this.mAdapter

        this.setSwipe()

        setObservers()
    }

    override fun onStart() {
        super.onStart()
        this.viewModel.setListeners()
        val date = binding.mainContent.calendarView.currentDate
        val monthYear = "%02d".format(date.month) + date.year
        this.viewModel.getMovimentations(monthYear)
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

    private fun setSwipe() {
        val itemTouch: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags,swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               alertDialogBuilder(viewHolder)
            }
        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(binding.mainContent.recyclerMovimentations)
    }

    private fun alertDialogBuilder(viewHolder: RecyclerView.ViewHolder) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Excluir Movimentação da conta")
            .setMessage("Você tem certeza que realmente deseja excluir essa movimentação?")
            .setCancelable(false)
            .setPositiveButton("Confirmar")  { _, _ ->
                val position = viewHolder.adapterPosition
                val movimentation = mAdapter.getMovimentation(position)
                val key = movimentation.key
                if (key != null) {
                    this.viewModel.removeMovimentation(key)
                    this.viewModel.updateUserIncome(-movimentation.value, movimentation.type)
                    mAdapter.notifyItemRemoved(position)
                }
            }
            .setNegativeButton("Cancelar")  { _, _ ->
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
                mAdapter.notifyDataSetChanged()
            }
            .create()
            .show()
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

        this.viewModel.movimentationData().observe(this, mAdapter::attachList)
    }

    override fun onStop() {
        this.viewModel.detachUserDataListener()
        this.viewModel.detachMovimentationDataListener()
        super.onStop()
    }
}