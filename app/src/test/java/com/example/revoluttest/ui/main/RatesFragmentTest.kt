package com.example.revoluttest.ui.main

import com.example.revoluttest.model.Currency
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RatesFragmentTest{

    @Test
    fun shouldMoveItemToTopOfList(){

        val string = "EUR"

        val list = ArrayList<Currency>()
        list.add(Currency("AAX", "aax", 1, 1.5))
        list.add(Currency("AAY", "aay", 1, 1.5))
        list.add(Currency("AAZ", "aaz", 1, 1.5))
        list.add(Currency("GBP", "aaz", 1, 1.5))
    }
}