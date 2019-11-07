package com.example.sberschoolcurrencyconverter.domain.interactor;

import androidx.annotation.Nullable;

import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ConversionInteractor {

    private static final int INTERNAL_SCALE = 5;
    private static final int PUBLIC_SCALE = 2;
    private final NumberFormat mNumberFormat = new DecimalFormat("#.##");

    @Nullable
    public String convert(@Nullable List<CurrencyEntity> currencies,
                          int fromCurrencyWithIndex,
                          int toCurrencyWithIndex,
                          @Nullable String amount) {
        BigDecimal parsedAmount = tryParseAmount(amount);
        if (currencies == null ||
                currencies.isEmpty() ||
                currencies.size() <= Math.max(fromCurrencyWithIndex, toCurrencyWithIndex) ||
                parsedAmount == null) {
            return null;
        }
        CurrencyEntity base = currencies.get(fromCurrencyWithIndex);
        CurrencyEntity quoted = currencies.get(toCurrencyWithIndex);
        BigDecimal result = parsedAmount
                .multiply(base.getValue())
                .multiply(new BigDecimal(quoted.getNominal()))
                .divide(quoted.getValue(), INTERNAL_SCALE, RoundingMode.HALF_UP)
                .divide(new BigDecimal(base.getNominal()), INTERNAL_SCALE, RoundingMode.HALF_UP);
        try {
            return mNumberFormat.format(result.setScale(PUBLIC_SCALE, RoundingMode.HALF_UP));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    private BigDecimal tryParseAmount(@Nullable String amount) {
        BigDecimal result;
        if (amount == null) {
            result = null;
        } else {
            try {
                result = new BigDecimal(amount);
            } catch (NumberFormatException e) {
                result = null;
            }
        }
        return result;
    }
}
