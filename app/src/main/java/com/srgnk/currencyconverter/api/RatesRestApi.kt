package com.srgnk.currencyconverter.api

import com.srgnk.currencyconverter.data.ExchangeRates
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class RatesRestApi @Inject constructor(private val exchangeRatesApi: ExchangeRatesApi): RatesApi {
    override suspend fun getRates(): Deferred<ExchangeRates> {
        return exchangeRatesApi.getRates()
    }
}