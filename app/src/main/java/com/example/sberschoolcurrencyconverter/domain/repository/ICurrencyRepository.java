package com.example.sberschoolcurrencyconverter.domain.repository;

import androidx.annotation.NonNull;

import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;

import java.io.IOException;
import java.util.List;

public interface ICurrencyRepository {

    @NonNull
    List<CurrencyEntity> loadCurrencies() throws IOException;
}
