package com.vcolofati.organizze.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vcolofati.organizze.R
import com.vcolofati.organizze.models.Movimentation

class MovimentationAdapter(private val context: Context): RecyclerView.Adapter<MovimentationAdapter.MovimentationViewHolder>() {

    private var movimentations: MutableList<Movimentation> = ArrayList()

    class MovimentationViewHolder(itemView: View,
                                  val textTitle: TextView = itemView.findViewById(R.id.textTitle),
                                  val textCategory: TextView = itemView.findViewById(R.id.textCategory),
                                  val textValue: TextView = itemView.findViewById(R.id.textValue)) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimentationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movimentation_recycler_item, parent, false)
        return MovimentationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovimentationViewHolder, position: Int) {
        val movement = movimentations[position]
        holder.textTitle.text = movement.description
        holder.textCategory.text = movement.category

        when(movement.type) {
            "d" -> {
                holder.textValue.setTextColor(context.resources.getColor(R.color.colorAccent))
                holder.textValue.text = "-${movement.value}"
            }
            "i" -> {
                holder.textValue.setTextColor(context.resources.getColor(R.color.colorAccentIncome))
                holder.textValue.text = "${movement.value}"
            }
        }
    }

    override fun getItemCount(): Int {
        return movimentations.size
    }

    fun attachList(movimentations: List<Movimentation>) {
        this.movimentations = movimentations.toMutableList()
        notifyDataSetChanged()
    }

    fun getMovimentation(position: Int): Movimentation {
        return movimentations[position]
    }
}