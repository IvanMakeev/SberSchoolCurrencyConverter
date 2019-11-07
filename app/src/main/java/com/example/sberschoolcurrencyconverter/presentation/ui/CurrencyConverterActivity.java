package com.example.sberschoolcurrencyconverter.presentation.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sberschoolcurrencyconverter.R;
import com.example.sberschoolcurrencyconverter.presentation.adapter.CurrencyAdapter;
import com.example.sberschoolcurrencyconverter.presentation.ui.viewmodel.CurrencyConverterViewModel;
import com.example.sberschoolcurrencyconverter.presentation.ui.viewmodel.CurrencyConverterViewModelFactory;

public class CurrencyConverterActivity extends AppCompatActivity {

    private static final int SECOND_ITEM = 1;
    private static final String SELECT_SPINNER_FROM = "SELECT_SPINNER_FROM";
    private static final String SELECT_SPINNER_TO = "SELECT_SPINNER_TO";

    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;
    private TextView mCurrentConvert;
    private EditText mEnterCurrency;
    private Button mConvertButton;
    private CurrencyConverterViewModel mViewModel;

    private boolean isNotFirstRun = false;
    private int mSelectedSpinnerFrom;
    private int mSelectedSpinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewModel();
        if (savedInstanceState != null) {
            mSelectedSpinnerFrom = savedInstanceState.getInt(SELECT_SPINNER_FROM);
            mSelectedSpinnerTo = savedInstanceState.getInt(SELECT_SPINNER_TO);
            isNotFirstRun = true;
        }
    }

    private void initView() {
        mSpinnerFrom = findViewById(R.id.spinner_from);
        mSpinnerTo = findViewById(R.id.spinner_to);
        mCurrentConvert = findViewById(R.id.current_convert);
        mEnterCurrency = findViewById(R.id.enter_currency);
        mConvertButton = findViewById(R.id.convert_button);

        mConvertButton.setOnClickListener(v -> mViewModel.convert(
                mSpinnerFrom.getSelectedItemPosition(),
                mSpinnerTo.getSelectedItemPosition(),
                mEnterCurrency.getText().toString()
        ));
    }

    private void initViewModel() {
        CurrencyConverterViewModelFactory factory = new CurrencyConverterViewModelFactory(getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory)
                .get(CurrencyConverterViewModel.class);

        mViewModel.loadCurrencies();
        mViewModel.getCurrencies().observe(this, currencyEntities -> {
            mSpinnerFrom.setAdapter(new CurrencyAdapter(currencyEntities));
            mSpinnerTo.setAdapter(new CurrencyAdapter(currencyEntities));
            if (isNotFirstRun) {
                mSpinnerFrom.setSelection(mSelectedSpinnerFrom);
                mSpinnerTo.setSelection(mSelectedSpinnerTo);
            } else {
                mSpinnerTo.setSelection(SECOND_ITEM);
            }
        });

        mViewModel.getResult().observe(this, resultConvert ->
                mCurrentConvert.setText(resultConvert));

        mViewModel.getErrors().observe(this, error ->
                Toast.makeText(this, R.string.conversion_error, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECT_SPINNER_FROM, mSpinnerFrom.getSelectedItemPosition());
        outState.putInt(SELECT_SPINNER_TO, mSpinnerTo.getSelectedItemPosition());
    }
}
