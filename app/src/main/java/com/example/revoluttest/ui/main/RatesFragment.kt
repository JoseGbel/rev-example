package com.example.revoluttest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.revoluttest.R
import com.example.revoluttest.model.Currency
import com.example.revoluttest.network.connectivity.NetworkStatus
import com.example.revoluttest.network.connectivity.NetworkStatusLiveData
import com.example.revoluttest.viewmodels.RatesViewModel
import kotlinx.android.synthetic.main.rates_fragment.*


/**
 * Simple fragment that uses a ViewModel to make a network call and observes the result
 * on a RecyclerView
 */
class RatesFragment : Fragment(), RatesRecyclerViewAdapter.OnRateListener {
    private lateinit var viewModel: RatesViewModel
    private lateinit var recyclerView : RecyclerView
    private val ratesArray = ArrayList<Currency>()
    private var ratesAdapter : RatesRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.rates_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rates_recycler_view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RatesViewModel::class.java)

        viewModel.initialisePositions()
        viewModel.setupTimer(viewModel.ratesPositions[0], context!!)

        val ratesLiveData = viewModel.rates
        ratesLiveData.observe(viewLifecycleOwner, Observer { data ->
            refreshData(data.rates)
            viewModel.sortListByPositionsArray(ratesArray)

            if (ratesAdapter == null) setUpRecyclerView()
            else {
                ratesAdapter!!.notifyDataSetChanged()
            }
        })

        NetworkStatusLiveData.observe(viewLifecycleOwner, Observer { status ->
            if(status == NetworkStatus.UNAVAILABLE)
            {
                Toast.makeText(context, getString(R.string.unavailable_internet), Toast.LENGTH_SHORT)
                    .show()
            }
            if(status == NetworkStatus.LOST)
            {
                Toast.makeText(context, getString(R.string.lost_internet), Toast.LENGTH_SHORT)
                    .show()
            }
            if(status == NetworkStatus.AVAILABLE)
            {
                val ratesLiveData = viewModel.rates
                ratesLiveData.observe(viewLifecycleOwner, Observer { data ->
                    refreshData(data.rates)
                    viewModel.sortListByPositionsArray(ratesArray)

                    if (ratesAdapter == null) setUpRecyclerView()
                    else {
                        ratesAdapter!!.notifyDataSetChanged()
                    }
                })
            }
        })
    }

    private fun refreshData(allRates: Map<String, Any>) {
        // clear the list to avoid new items being re-appended
        ratesArray.clear()

        ratesArray.add(Currency("EUR", "Euro",
            R.drawable.eur, allRates["eur"].toString().toDouble()))
        ratesArray.add(Currency("AUD", "Australian Dollar",
            R.drawable.aud, allRates["aud"].toString().toDouble()))
        ratesArray.add(Currency("BGN", "Bulgarian Lev",
            R.drawable.bgn, allRates["bgn"].toString().toDouble()))
        ratesArray.add(Currency("BRL", "Brazilian Real",
            R.drawable.brl, allRates["brl"].toString().toDouble()))
        ratesArray.add(Currency("CAD", "Canadian Dollar",
            R.drawable.cad, allRates["cad"].toString().toDouble()))
        ratesArray.add(Currency("CHF", "Swiss franc",
            R.drawable.chf, allRates["chf"].toString().toDouble()))
        ratesArray.add(Currency("CNY", "Chinese Yuan",
            R.drawable.cny, allRates["cny"].toString().toDouble()))
        ratesArray.add(Currency("CZK", "Czech Koruna",
            R.drawable.czk, allRates["czk"].toString().toDouble()))
        ratesArray.add(Currency("DKK", "Danish Krone",
            R.drawable.dkk, allRates["dkk"].toString().toDouble()))
        ratesArray.add(Currency("GBP", "British Pound",
            R.drawable.gbp, allRates["gbp"].toString().toDouble()))
        ratesArray.add(Currency("HKD", "Hong Kong Dollar",
            R.drawable.hkd, allRates["hkd"].toString().toDouble()))
        ratesArray.add(Currency("HRK", "Croatian Kuna",
            R.drawable.hrk, allRates["hrk"].toString().toDouble()))
        ratesArray.add(Currency("HUF", "Hungarian Forint",
            R.drawable.huf, allRates["huf"].toString().toDouble()))
        ratesArray.add(Currency("IDR", "Indonesian Rupiah",
            R.drawable.idr, allRates["idr"].toString().toDouble()))
        ratesArray.add(Currency("ILS", "Israeli Shekel",
            R.drawable.ils, allRates["ils"].toString().toDouble()))
        ratesArray.add(Currency("INR", "Indian Rupee",
            R.drawable.inr, allRates["inr"].toString().toDouble()))
        ratesArray.add(Currency("ISK", "Icelandic Króna",
            R.drawable.isk, allRates["isk"].toString().toDouble()))
        ratesArray.add(Currency("JPY", "Japanese Yen",
            R.drawable.jpy, allRates["jpy"].toString().toDouble()))
        ratesArray.add(Currency("KRW", "South Korean Won",
            R.drawable.krw, allRates["krw"].toString().toDouble()))
        ratesArray.add(Currency("MXN", "Mexican Peso",
            R.drawable.mxn, allRates["mxn"].toString().toDouble()))
        ratesArray.add(Currency("MYR", "Malaysian Ringgit",
            R.drawable.myr, allRates["myr"].toString().toDouble()))
        ratesArray.add(Currency("NOK", "Norwegian Krone",
            R.drawable.nok, allRates["nok"].toString().toDouble()))
        ratesArray.add(Currency("NZD", "Bulgarian Lev",
            R.drawable.bgn, allRates["bgn"].toString().toDouble()))
        ratesArray.add(Currency("PHP", "Philippine Peso",
            R.drawable.php, allRates["php"].toString().toDouble()))
        ratesArray.add(Currency("PLN", "Polish Złoty",
            R.drawable.pln, allRates["pln"].toString().toDouble()))
        ratesArray.add(Currency("RON", "Romanian Leu",
            R.drawable.ron, allRates["ron"].toString().toDouble()))
        ratesArray.add(Currency("RUB", "Russian Rouble",
            R.drawable.rub, allRates["rub"].toString().toDouble()))
        ratesArray.add(Currency("SEK", "Swedish krona",
            R.drawable.sek, allRates["sek"].toString().toDouble()))
        ratesArray.add(Currency("SGD", "Singapore Dollar",
            R.drawable.sgd, allRates["sgd"].toString().toDouble()))
        ratesArray.add(Currency("THB", "Thai Baht",
            R.drawable.thb, allRates["thb"].toString().toDouble()))
        ratesArray.add(Currency("USD", "United States Dollar",
            R.drawable.usd, allRates["usd"].toString().toDouble()))
        ratesArray.add(Currency("ZAR", "South African Rand",
            R.drawable.zar, allRates["zar"].toString().toDouble()))
    }

    private fun setUpRecyclerView(){
        if (ratesAdapter == null){
            ratesAdapter = RatesRecyclerViewAdapter(ratesArray, context, this)
            rates_recycler_view.layoutManager = LinearLayoutManager(context)
            rates_recycler_view.adapter = ratesAdapter
        } else {
            ratesAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRateClick(position: Int, currency: String) {
        // stop previous timer
        viewModel.stopCurrentTimer()

        // place the touched rate at the top of the list and keep the elements positions
        viewModel.moveBaseCurrencyToTopOf(ratesArray, currency)
        viewModel.recordPositions(ratesArray)

        // move the touched element to the top of the RecyclerView
        ratesAdapter?.notifyItemMoved(position, 0)
        recyclerView.scrollToPosition(0)

        // restart the new timerS
        viewModel.setupTimer(currency, context!!)
    }

    override fun onStop() {
        super.onStop()

        // Stop calling the network
        viewModel.stopCurrentTimer()
    }
}
