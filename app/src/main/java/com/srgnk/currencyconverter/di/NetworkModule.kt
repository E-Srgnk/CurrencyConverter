package com.srgnk.currencyconverter.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.srgnk.currencyconverter.api.ExchangeRatesApi
import com.srgnk.currencyconverter.api.RatesApi
import com.srgnk.currencyconverter.api.RatesRestApi
import com.srgnk.currencyconverter.commons.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRatesApi(exchangeRatesApi: ExchangeRatesApi): RatesApi = RatesRestApi(exchangeRatesApi)

    @Singleton
    @Provides
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi = retrofit.create(ExchangeRatesApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}