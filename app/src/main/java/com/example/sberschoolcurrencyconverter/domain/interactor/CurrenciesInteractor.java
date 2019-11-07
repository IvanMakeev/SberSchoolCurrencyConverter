package com.example.sberschoolcurrencyconverter.domain.interactor;

import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;
import com.example.sberschoolcurrencyconverter.domain.repository.ICurrencyRepository;

import java.io.IOException;
import java.util.List;

public class CurrenciesInteractor {

    private final ICurrencyRepository mCurrencyRepository;

    public CurrenciesInteractor(ICurrencyRepository currencyRepository) {
        mCurrencyRepository = currencyRepository;
    }

    public List<CurrencyEntity> loadCurrencies() throws IOException {
        return mCurrencyRepository.loadCurrencies();
    }
}
