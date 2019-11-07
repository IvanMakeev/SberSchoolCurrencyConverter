package com.example.sberschoolcurrencyconverter.data.api;

import com.example.sberschoolcurrencyconverter.data.model.CurrenciesData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICurrencyApi {
    @GET("scripts/XML_daily.asp")
    Call<CurrenciesData> loadCurrencies();
}
