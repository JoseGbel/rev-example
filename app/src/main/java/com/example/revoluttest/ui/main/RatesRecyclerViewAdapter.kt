package com.example.revoluttest.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import kotlinx.android.synthetic.main.rates_card_layout.view.*


/**
 * Adapter class that inflates a layout of the CardView and binds the data to it
 */
class RatesRecyclerViewAdapter(val ratesList: ArrayList<Currency>, val context: Context?)
    : RecyclerView.Adapter<RatesRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(currency : Currency){
            itemView.currency_name.text = currency.currencyName
            itemView.country_currency_name.text = currency.countryCurrencyName
            itemView.value.text = currency.value.toString()
            itemView.flag_image_view.setImageResource(currency.flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rates_card_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(ratesList[position])
    }
}