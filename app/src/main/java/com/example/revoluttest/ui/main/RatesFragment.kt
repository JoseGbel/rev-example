package com.example.revoluttest.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import kotlinx.android.synthetic.main.rates_fragment.*

class RatesFragment : Fragment() {

    companion object {
        fun newInstance() = RatesFragment()
    }

    private lateinit var viewModel: RatesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.rates_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)

        val ratesList = ArrayList<Currency>()

        ratesList.add(Currency("EUR", "European Euro", R.drawable.eur, 100))
        ratesList.add(Currency("USD", "American Dollar", R.drawable.usd, 200))
        ratesList.add(Currency("CAD", "Canadian Dollar", R.drawable.cad, 300))

        val ratesAdapter = RatesRecyclerViewAdapter(ratesList, context)

        rates_recycler_view.layoutManager = LinearLayoutManager(context)
        rates_recycler_view.adapter = ratesAdapter
    }

}
