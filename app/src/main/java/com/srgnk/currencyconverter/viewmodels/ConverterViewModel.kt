package com.srgnk.currencyconverter.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConverterViewModel : ViewModel() {

    val enteredValue: MutableLiveData<String> = MutableLiveData("0")

    fun addSymbol(symbol: String) {
        val value = enteredValue.value
        value?.let {
            when (symbol) {
                "," -> {
                    if (it.contains(",")) return
                    enteredValue.value = value + symbol
                }
                "0" -> {
                    if (it == "0") return
                    enteredValue.value = value + symbol
                }
                else -> {
                    if (value == "0")
                        enteredValue.value = symbol
                    else
                        enteredValue.value = value + symbol
                }
            }
        }
    }

    fun clearAll() {
        enteredValue.value = "0"
    }

    fun clearLast() {
        enteredValue.value?.let {
            if (it == "0") return
            if (it.length == 1)
                enteredValue.value = "0"
            else
                enteredValue.value = it.substring(0, it.length - 1)
        }
    }

}