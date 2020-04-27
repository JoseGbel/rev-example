package com.example.revoluttest.ui.main

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import com.example.revoluttest.util.round
import kotlinx.android.synthetic.main.converter_card_layout.view.*
import java.lang.NumberFormatException
import kotlin.properties.Delegates


/**
 * Adapter class that inflates a layout of the CardView and binds the data to it
 */
class ConverterRecyclerViewAdapter(
    val ratesList: ArrayList<Currency>,
    val context: Context?,
    val onRateListener: OnRateListener,
    val onFocusChangeListener: OnFocusChangeListener
) : RecyclerView.Adapter<ConverterRecyclerViewAdapter.ViewHolder>() {

    private val doubleLiveData = MutableLiveData<Double>()

    override fun getItemId(position: Int): Long {
        return ratesList[position].currencyName.hashCode().toLong()
    }

    class ViewHolder(
        itemView: View,
        val onRateListener: OnRateListener,
        private val multiplier: MutableLiveData<Double>,
        val onFocusChangeListener: OnFocusChangeListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnFocusChangeListener {

        var editText: EditText

        private var currentValue: Double by Delegates.notNull()

        init {
            itemView.setOnClickListener(this)
            editText = itemView.findViewById(R.id.value_conv_cv)

            multiplier.observeForever {
                if (!editText.hasFocus()){
                    val price = currentValue * multiplier.value!!
                    editText.text = SpannableStringBuilder(price.round(2).toString())
                }
            }
        }

        fun bindItem(currency: Currency) {
            itemView.currency_name_conv_cv.text = currency.currencyName
            itemView.country_currency_name_conv_cv.text = currency.countryCurrencyName
            itemView.flag_iv_conv_cv.setImageResource(currency.flag)

            if (editText.hasFocus()) {
                editText.addTextChangedListener {
                    try {
                        multiplier.value = it.toString().toDouble()
                    } catch (e: NumberFormatException) {
                        multiplier.value = 0.0
                    }
                }
            }

            if(!editText.hasFocus()){
                if(multiplier.value != null) {
                    val price = currency.value * multiplier.value!!
                    editText.text = SpannableStringBuilder(price.round(2).toString())
                } else {
                    editText.text = SpannableStringBuilder(currency.value.round(2)
                        .toString())
                }
            }

            // keep the value of the currency to multiply it by the multiplier
            // when the multiplier emits changes
            currentValue = currency.value
        }

        override fun onClick(v: View?) {
            onRateListener.onRateClick(
                adapterPosition,
                itemView.currency_name_conv_cv.text.toString(),
                SpannableStringBuilder(itemView.value_conv_cv.text)
                    .toString()
                    .toDouble()
                    .round(2)
            )
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            val currency = Currency(
                itemView.currency_name_conv_cv.text.toString(),
                itemView.country_currency_name_conv_cv.text.toString(),
                itemView.flag_iv_conv_cv.id,
                SpannableStringBuilder(itemView.value_conv_cv.text)
                    .toString()
                    .toDouble()
                    .round(2)
            )
            if (hasFocus)
                onFocusChangeListener.onFocusChange(adapterPosition, currency)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.converter_card_layout, parent, false)

        return ViewHolder(view, onRateListener, doubleLiveData, onFocusChangeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.layoutPosition == 0){
            holder.editText.isFocusable = true
            holder.editText.isClickable = false
        }

        if (holder.layoutPosition != 0){
            holder.editText.isFocusable = false
            holder.editText.isClickable = true
        }

        holder.bindItem(ratesList[position])
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    interface OnRateListener {
        fun onRateClick(position: Int, currency: String, currentPrice: Double)
        // todo trying to keep the old price value when going to top
        fun onRateClick2(position: Int, currency: Currency)

    }

    interface OnFocusChangeListener {
        fun onFocusChange(position: Int, currency: Currency)
    }
}
