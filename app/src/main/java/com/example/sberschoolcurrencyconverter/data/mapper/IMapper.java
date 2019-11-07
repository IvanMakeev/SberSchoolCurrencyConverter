package com.example.sberschoolcurrencyconverter.data.mapper;

public interface IMapper<E, D> {

    E mapToEntity(D type);
}
