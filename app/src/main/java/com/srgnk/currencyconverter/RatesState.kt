package com.srgnk.currencyconverter

import com.srgnk.currencyconverter.data.ExchangeRates

sealed class RatesState {
    class Success(val exchangeRates: ExchangeRates): RatesState()
    class Error(val message: String?): RatesState()
}