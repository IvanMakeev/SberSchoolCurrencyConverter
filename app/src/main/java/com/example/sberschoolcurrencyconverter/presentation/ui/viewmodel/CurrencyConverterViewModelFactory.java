package com.example.sberschoolcurrencyconverter.presentation.ui.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sberschoolcurrencyconverter.data.mapper.CurrencyMapper;
import com.example.sberschoolcurrencyconverter.data.repository.ServerCurrencyRepository;
import com.example.sberschoolcurrencyconverter.domain.interactor.ConversionInteractor;
import com.example.sberschoolcurrencyconverter.domain.interactor.CurrenciesInteractor;
import com.example.sberschoolcurrencyconverter.domain.repository.ICurrencyRepository;
import com.example.sberschoolcurrencyconverter.presentation.utils.IResourceWrapper;
import com.example.sberschoolcurrencyconverter.presentation.utils.ResourceWrapper;

public class CurrencyConverterViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context mApplicationContext;

    public CurrencyConverterViewModelFactory(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (CurrencyConverterViewModel.class.equals(modelClass)) {
            ICurrencyRepository currencyRepository = new ServerCurrencyRepository(new CurrencyMapper());
            CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor(currencyRepository);
            ConversionInteractor conversionInteractor = new ConversionInteractor();
            IResourceWrapper wrapper = new ResourceWrapper(mApplicationContext.getResources());

            return (T) new CurrencyConverterViewModel(currenciesInteractor, conversionInteractor, wrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
