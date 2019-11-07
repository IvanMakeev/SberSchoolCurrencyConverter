package com.example.sberschoolcurrencyconverter.presentation.ui.viewmodel;

import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sberschoolcurrencyconverter.R;
import com.example.sberschoolcurrencyconverter.domain.interactor.ConversionInteractor;
import com.example.sberschoolcurrencyconverter.domain.interactor.CurrenciesInteractor;
import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;
import com.example.sberschoolcurrencyconverter.presentation.utils.IResourceWrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CurrencyConverterViewModel extends ViewModel {

    private CurrenciesInteractor mCurrenciesInteractor;
    private ConversionInteractor mConversionInteractor;
    private IResourceWrapper mResourceWrapper;
    private List<CurrencyEntity> mEntityList;
    private final CurrencyEntity mRub;
    private final MutableLiveData<List<CurrencyEntity>> mCurrencies = new MutableLiveData<>();
    private final MutableLiveData<String> mResult = new MutableLiveData<>();
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();


    public CurrencyConverterViewModel(CurrenciesInteractor currenciesInteractor,
                                      ConversionInteractor conversionInteractor,
                                      IResourceWrapper wrapper) {
        mCurrenciesInteractor = currenciesInteractor;
        mConversionInteractor = conversionInteractor;
        mResourceWrapper = wrapper;
        mRub = new CurrencyEntity(
                "rub_id",
                "RUB",
                1,
                mResourceWrapper.getString(R.string.russian_ruble),
                BigDecimal.ONE
        );
    }

    public void loadCurrencies() {
        new CurrencyAsyncTask().execute(mCurrenciesInteractor);
    }

    public void convert(int fromCurrencyWithIndex, int toCurrencyWithIndex, @Nullable String amount) {
        List<CurrencyEntity> currencies = mCurrencies.getValue();
        String converted = mConversionInteractor.convert(currencies, fromCurrencyWithIndex, toCurrencyWithIndex, amount);
        if (converted == null) {
            mErrors.setValue(mResourceWrapper.getString(R.string.conversion_error));
        } else {
            CurrencyEntity quoted = currencies.get(toCurrencyWithIndex);
            String resultConvert = mResourceWrapper.getString(R.string.you_will_get, converted, quoted.getCharCode());
            mResult.setValue(resultConvert);
        }
    }

    public LiveData<List<CurrencyEntity>> getCurrencies() {
        return mCurrencies;
    }

    public LiveData<String> getResult() {
        return mResult;
    }

    public LiveData<String> getErrors() {
        return mErrors;
    }

    private class CurrencyAsyncTask extends AsyncTask<CurrenciesInteractor, Void, Void> {

        @Override
        protected Void doInBackground(CurrenciesInteractor... currenciesInteractors) {
            try {
                mEntityList = currenciesInteractors[currenciesInteractors.length - 1].loadCurrencies();
                if (!mEntityList.contains(mRub)) {
                    mEntityList.add(0, mRub);
                }
                mCurrencies.postValue(mEntityList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
