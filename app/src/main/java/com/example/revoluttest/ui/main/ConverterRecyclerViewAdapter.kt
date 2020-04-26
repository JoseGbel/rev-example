package com.example.revoluttest.ui.main

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import kotlinx.android.synthetic.main.converter_card_layout.view.*


/**
 * Adapter class that inflates a layout of the CardView and binds the data to it
 */
class ConverterRecyclerViewAdapter(val ratesList: ArrayList<Currency>,
                                   val context: Context?,
                                   val onRateListener: OnRateListener)
    : RecyclerView.Adapter<ConverterRecyclerViewAdapter.ViewHolder>() {

    var clickedItem = -1

    class ViewHolder(itemView: View, val onRateListener: OnRateListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var editText: EditText

        init{
            itemView.setOnClickListener(this)
            editText = itemView.findViewById(R.id.value_conv_cv)
        }

        fun getEtCodeBoxText(): String? {
            return editText.text.toString()
        }


        fun bindItem(currency : Currency){
            itemView.currency_name_conv_cv.text = currency.currencyName
            itemView.country_currency_name_conv_cv.text = currency.countryCurrencyName
            itemView.value_conv_cv.text = SpannableStringBuilder(currency.value.toString())
            itemView.flag_iv_conv_cv.setImageResource(currency.flag)
            itemView.tag = currency.currencyName
        }

        fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
            val smoothScroller = object : LinearSmoothScroller(this.context) {
                override fun getVerticalSnapPreference(): Int = snapMode
                override fun getHorizontalSnapPreference(): Int = snapMode
            }
            smoothScroller.targetPosition = position
            layoutManager?.startSmoothScroll(smoothScroller)
        }

        override fun onClick(v: View?) {
            onRateListener.onRateClick(adapterPosition, itemView.currency_name_conv_cv.text.toString())
        }
    }

    interface OnRateListener {
        fun onRateClick(position : Int, currency : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.converter_card_layout, parent, false)

        return ViewHolder(view, onRateListener)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = ratesList[position]
        holder.bindItem(current)
    }
}