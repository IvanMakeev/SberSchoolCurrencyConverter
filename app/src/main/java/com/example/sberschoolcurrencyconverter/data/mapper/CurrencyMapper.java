package com.example.sberschoolcurrencyconverter.data.mapper;

import com.example.sberschoolcurrencyconverter.data.model.CurrencyData;
import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

public class CurrencyMapper implements IMapper<List<CurrencyEntity>, List<CurrencyData>> {

    @Override
    public List<CurrencyEntity> mapToEntity(List<CurrencyData> type) {
        List<CurrencyEntity> result = new ArrayList<>();
        for (CurrencyData currency : type) {
            result.add(new CurrencyEntity(
                    currency.getId(),
                    currency.getCharCode(),
                    currency.getNominal(),
                    currency.getName(),
                    currency.getValue()
            ));
        }
        return result;
    }
}
