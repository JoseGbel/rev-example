package com.example.revoluttest.ui.main

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
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

    private val doubleLiveData = MutableLiveData<Double>()

    class ViewHolder(itemView: View, val onRateListener: OnRateListener, private val doubleLiveData: MutableLiveData<Double>) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var editText: EditText

        init{
            itemView.setOnClickListener(this)
            editText = itemView.findViewById(R.id.value_conv_cv)

            doubleLiveData.observeForever {
//                if (!itemView.hasFocus()){
//                    editText.text = SpannableStringBuilder(it.toString())
//                }
            }
        }

        fun bindHeader(currency: Currency) {

            Log.d("FOCUSS", itemView.value_conv_cv.hasFocus().toString())
            if (!editText.hasFocus()) {
                itemView.value_conv_cv.text = SpannableStringBuilder("2.0")
                itemView.currency_name_conv_cv.text = currency.currencyName
                itemView.country_currency_name_conv_cv.text = currency.countryCurrencyName
                itemView.flag_iv_conv_cv.setImageResource(currency.flag)
                itemView.tag = currency.currencyName
            }
        }

        fun bindItem(currency : Currency){
            itemView.value_conv_cv.addTextChangedListener {
                doubleLiveData.value = it.toString().toDouble()
            }

//            if(adapterPosition != 0)
//                itemView.value_conv_cv.text = SpannableStringBuilder(currency.value.toString())
//            else
//                itemView.value_conv_cv.text= SpannableStringBuilder("1.0")

            itemView.value_conv_cv.text = SpannableStringBuilder(currency.value.toString())

            itemView.currency_name_conv_cv.text = currency.currencyName
            itemView.country_currency_name_conv_cv.text = currency.countryCurrencyName
            itemView.flag_iv_conv_cv.setImageResource(currency.flag)
            itemView.tag = currency.currencyName
        }

        override fun onClick(v: View?) {
            onRateListener.onRateClick(adapterPosition, itemView.currency_name_conv_cv.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.converter_card_layout, parent, false)

        return ViewHolder(view, onRateListener, doubleLiveData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.adapterPosition == 0){
            holder.bindHeader(ratesList[position])
        }
        else
            holder.bindItem(ratesList[position])
    }



    override fun getItemCount(): Int {
        return ratesList.size
    }

    interface OnRateListener {
        fun onRateClick(position : Int, currency : String)
    }
}