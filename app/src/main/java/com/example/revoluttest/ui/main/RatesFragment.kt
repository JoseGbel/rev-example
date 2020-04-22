package com.example.revoluttest.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import com.example.revoluttest.model.Rate
import com.example.revoluttest.viewmodels.RatesViewModel
import kotlinx.android.synthetic.main.rates_fragment.*

class RatesFragment : Fragment() {
    private lateinit var viewModel: RatesViewModel

    private var ratesAdapter : RatesRecyclerViewAdapter? = null

    private val ratesList = ArrayList<Currency>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.rates_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RatesViewModel::class.java)
        viewModel.init("EUR")

        Log.d("RETRORESPONSE", "value: " + viewModel.getRatesRepository().value)

        viewModel.getRatesRepository().observe(viewLifecycleOwner, Observer { response ->

//            Log.d("RETRORESPONSE", response.rates.toString())

//            val allRates: Map<String, Float> = response.rates
            val allRates: List<Rate> = response.rates

            for (rate in allRates) {
                when (rate.currency){
                    "EUR" -> ratesList.add(Currency("EUR", "Euro", R.drawable.aud, rate.value))
                    "AUD" -> ratesList.add(Currency("AUD", "Australian Dollar", R.drawable.aud, rate.value))
                    "BGN" -> ratesList.add(Currency("BGN", "Bulgarian Lev", R.drawable.aud, rate.value))
                    "BRL" -> ratesList.add(Currency("BRL", "Brazilian Real", R.drawable.aud, rate.value))
                    "CAD" -> ratesList.add(Currency("CAD", "Canadian Dollar", R.drawable.aud, rate.value))

                }
            }
            if (ratesAdapter == null){
                setUpRecyclerView()
            } else{
                ratesAdapter!!.notifyDataSetChanged()
            }
        })

//        ratesList.add(Currency("EUR", "European Euro", R.drawable.eur, 100))
//        ratesList.add(Currency("USD", "American Dollar", R.drawable.usd, 200))
//        ratesList.add(Currency("CAD", "Canadian Dollar", R.drawable.cad, 300))
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        if (ratesAdapter == null){
            ratesAdapter = RatesRecyclerViewAdapter(ratesList, context)

            rates_recycler_view.layoutManager = LinearLayoutManager(context)
            rates_recycler_view.adapter = ratesAdapter
        } else {
            ratesAdapter!!.notifyDataSetChanged()
        }
    }

}
