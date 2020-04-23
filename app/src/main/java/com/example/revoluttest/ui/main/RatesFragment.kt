package com.example.revoluttest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkerParameters
import com.example.revoluttest.DataFetchingWorker
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import com.example.revoluttest.model.Rates
import com.example.revoluttest.viewmodels.RatesViewModel
import kotlinx.android.synthetic.main.rates_fragment.*

/**
 * Simple fragment that uses a ViewModel to make a network call and observes the result
 * on a RecyclerView
 */
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

//        DataFetchingWorker.periodicWork()

        val ratesLiveData = viewModel.getRatesRepository()

            addElements(ratesLiveData.rates!!)

            if (ratesAdapter == null){
                setUpRecyclerView()
            } else{
                ratesAdapter!!.notifyDataSetChanged()
            }

        setUpRecyclerView()
    }

    private fun addElements(allRates: Rates) {
        ratesList.add(Currency(
            "EUR", "Euro", R.drawable.eur, 1.00))
        ratesList.add(Currency(
            "AUD", "Australian Dollar", R.drawable.aud, allRates.AUD))
        ratesList.add(Currency(
            "BGN", "Bulgarian Lev", R.drawable.bgn, allRates.BGN))
        ratesList.add(Currency(
            "BRL", "Brazilian Real", R.drawable.brl, allRates.BRL))
        ratesList.add(Currency(
            "CAD", "Canadian Dollar", R.drawable.cad, allRates.CAD))
        ratesList.add(Currency(
            "CHF", "Swiss franc", R.drawable.chf, allRates.CHF))
        ratesList.add(Currency(
            "CNY", "Chinese Yuan", R.drawable.cny, allRates.CNY))
        ratesList.add(Currency(
            "CZK", "Czech Koruna", R.drawable.czk, allRates.CZK))
        ratesList.add(Currency(
            "DKK", "Danish Krone", R.drawable.dkk, allRates.DKK))
        ratesList.add(Currency(
            "GBP", "British Pound", R.drawable.gbp, allRates.GBP))
        ratesList.add(Currency(
            "HKD", "Hong Kong Dollar", R.drawable.hkd, allRates.HKD))
        ratesList.add(Currency(
            "HRK", "Croatian Kuna", R.drawable.hrk, allRates.HRK))
        ratesList.add(Currency(
            "HUF", "Hungarian Forint", R.drawable.huf, allRates.HUF))
        ratesList.add(Currency(
            "IDR", "Indonesian Rupiah", R.drawable.idr, allRates.IDR))
        ratesList.add(Currency(
            "ILS", "Israeli Shekel", R.drawable.ils, allRates.ILS))
        ratesList.add(Currency(
            "INR", "Indian Rupee", R.drawable.eur, allRates.INR))
        ratesList.add(Currency(
            "ISK", "Icelandic Króna", R.drawable.isk, allRates.ISK))
        ratesList.add(Currency(
            "JPY", "Japanese Yen", R.drawable.jpy, allRates.JPY))
        ratesList.add(Currency(
            "KRW", "South Korean Won", R.drawable.krw, allRates.KRW))
        ratesList.add(Currency(
            "MXN", "Mexican Peso", R.drawable.mxn, allRates.MXN))
        ratesList.add(Currency(
            "MYR", "Malaysian Ringgit", R.drawable.myr, allRates.MYR))
        ratesList.add(Currency(
            "NOK", "Norwegian Krone", R.drawable.nok, allRates.NOK))
        ratesList.add(Currency(
            "NZD", "Bulgarian Lev", R.drawable.bgn, allRates.BGN))
        ratesList.add(Currency(
            "PHP", "Philippine Peso", R.drawable.php, allRates.PHP))
        ratesList.add(Currency(
            "PLN", "Polish Złoty", R.drawable.pln, allRates.PLN))
        ratesList.add(Currency(
            "RON", "Romanian Leu", R.drawable.ron, allRates.RON))
        ratesList.add(Currency(
            "RUB", "Russian Rouble", R.drawable.rub, allRates.RUB))
        ratesList.add(Currency(
            "SEK", "Swedish krona", R.drawable.sek, allRates.SEK))
        ratesList.add(Currency(
            "SGD", "Singapore Dollar", R.drawable.sgd, allRates.SGD))
        ratesList.add(Currency(
            "THB", "Thai Baht", R.drawable.thb, allRates.THB))
        ratesList.add(Currency(
            "USD", "United States Dollar", R.drawable.usd, allRates.USD))
        ratesList.add(Currency(
            "ZAR", "South African Rand", R.drawable.zar, allRates.ZAR))
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
