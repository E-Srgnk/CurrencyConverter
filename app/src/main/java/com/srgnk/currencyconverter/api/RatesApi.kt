package com.srgnk.currencyconverter.api

import com.srgnk.currencyconverter.data.ExchangeRates
import kotlinx.coroutines.Deferred

interface RatesApi {
    suspend fun getRates(): Deferred<ExchangeRates>
}