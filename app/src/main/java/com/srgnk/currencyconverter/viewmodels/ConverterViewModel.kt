package com.srgnk.currencyconverter.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srgnk.currencyconverter.RatesState
import com.srgnk.currencyconverter.api.RatesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val api: RatesApi) : ViewModel() {

    val enteredValue: MutableLiveData<String> = MutableLiveData("0")
    val calculatedValue: MutableLiveData<String> = MutableLiveData("0")
    val ratesState: MutableLiveData<RatesState> = MutableLiveData()
    val currencyName: MutableLiveData<String> = MutableLiveData()
    val ratio: MutableLiveData<String> = MutableLiveData()

    var rates: Map<String, Float> = hashMapOf()

    fun fetchRates() {
        viewModelScope.launch {
            try {
                val result = api.getRates().await()
                rates = result.rates
                ratio.value = "1 EUR = " + String.format(
                    "%.2f",
                    rates[currencyName.value]
                ) + " ${currencyName.value}"
                ratesState.postValue(RatesState.Success(result))
            } catch (e: Throwable) {
                ratesState.postValue(RatesState.Error(e.message))
            }
        }
    }

    fun selectedCurrency(currency: String) {
        currencyName.value = currency
    }

    fun addSymbol(symbol: String) {
        enteredValue.value?.let { value ->
            when (symbol) {
                "," -> {
                    if (value.contains(",")) return
                    enteredValue.value = value + symbol
                }
                "0" -> {
                    if (value == "0") return
                    enteredValue.value = value + symbol
                }
                else -> {
                    if (value == "0")
                        enteredValue.value = symbol
                    else
                        enteredValue.value = value + symbol
                }
            }
            calculateResult()
        }
    }

    private fun calculateResult() {
        val ratio: Float = rates[currencyName.value]!!
        val value: Float = enteredValue.value!!.replace(",", ".").toFloat()
        calculatedValue.value = (ratio * value).toString().replace(".", ",")
    }

    fun clearAll() {
        enteredValue.value = "0"
        calculatedValue.value = "0"
    }

    fun clearLast() {
        enteredValue.value?.let { value ->
            if (value == "0") return
            if (value.length == 1) {
                enteredValue.value = "0"
                calculatedValue.value = "0"
            } else {
                enteredValue.value = value.substring(0, value.length - 1)
                calculateResult()
            }
        }
    }
}