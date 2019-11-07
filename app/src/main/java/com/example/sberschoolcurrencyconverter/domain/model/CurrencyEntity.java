package com.example.sberschoolcurrencyconverter.domain.model;

import java.math.BigDecimal;

public class CurrencyEntity {

    private String mId;
    private String mCharCode;
    private long mNominal;
    private String mName;
    private BigDecimal mValue;

    public CurrencyEntity(String id, String charCode, long nominal, String name, BigDecimal value) {
        mId = id;
        mCharCode = charCode;
        mNominal = nominal;
        mName = name;
        mValue = value;
    }

    public String getId() {
        return mId;
    }

    public String getCharCode() {
        return mCharCode;
    }

    public long getNominal() {
        return mNominal;
    }

    public String getName() {
        return mName;
    }

    public BigDecimal getValue() {
        return mValue;
    }
}
