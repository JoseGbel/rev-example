package com.example.revoluttest.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import kotlinx.android.synthetic.main.rates_card_layout.view.*


/**
 * Adapter class that inflates a layout of the CardView and binds the data to it
 */
class RatesRecyclerViewAdapter(val ratesList: ArrayList<Currency>,
                               val context: Context?,
                               val onRateListener: OnRateListener)
    : RecyclerView.Adapter<RatesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, val onRateListener: OnRateListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        init{
            itemView.setOnClickListener(this)
        }

        fun bindItem(currency : Currency) {
            itemView.currency_name_rates_cv.text = currency.currencyName
            itemView.country_currency_name_rates_cv.text = currency.countryCurrencyName
            itemView.value_rates_cv.text = currency.value.toString()

            itemView.flag_iv_rates_cv.setImageResource(currency.flag)

        }

        override fun onClick(v: View?) {
            onRateListener.onRateClick(adapterPosition, itemView.currency_name_rates_cv.text.toString())
        }
    }



    interface OnRateListener {
        fun onRateClick(position : Int, currency : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rates_card_layout, parent, false)

        return ViewHolder(view, onRateListener)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(ratesList[position])
    }
}