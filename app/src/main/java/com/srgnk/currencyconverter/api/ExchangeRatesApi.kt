package com.srgnk.currencyconverter.api

import com.srgnk.currencyconverter.data.ExchangeRates
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ExchangeRatesApi {
    @GET("v1/latest?access_key=e79a58b440ef1f5de5f726340ac02e9b")
    fun getRates(): Deferred<ExchangeRates>
}