package com.example.sberschoolcurrencyconverter.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Chumak
 **/
public class CurrencyAdapter extends BaseAdapter {

    private final List<CurrencyEntity> mCurrencies;

    public CurrencyAdapter(@NonNull List<CurrencyEntity> currencies) {
        mCurrencies = new ArrayList<>(currencies);
    }

    @Override
    public int getCount() {
        return mCurrencies.size();
    }

    @Override
    public CurrencyEntity getItem(int position) {
        return mCurrencies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            Holder holder = new Holder(convertView);
            convertView.setTag(holder);
        }
        CurrencyEntity currency = getItem(position);
        Holder holder = (Holder) convertView.getTag();
        if (currency != null) {
            String text = currency.getName();
            holder.bind(text);
        }
        return convertView;
    }

    private static class Holder {
        private TextView mText;

        private Holder(View view) {
            mText = view.findViewById(android.R.id.text1);
        }

        void bind(String text){
            mText.setText(text);
        }

    }
}
