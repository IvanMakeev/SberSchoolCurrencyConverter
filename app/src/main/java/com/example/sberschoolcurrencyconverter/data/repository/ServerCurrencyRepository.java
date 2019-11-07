package com.example.sberschoolcurrencyconverter.data.repository;

import androidx.annotation.NonNull;

import com.example.sberschoolcurrencyconverter.BuildConfig;
import com.example.sberschoolcurrencyconverter.data.api.ICurrencyApi;
import com.example.sberschoolcurrencyconverter.data.mapper.CurrencyMapper;
import com.example.sberschoolcurrencyconverter.data.model.CurrenciesData;
import com.example.sberschoolcurrencyconverter.data.model.CurrencyData;
import com.example.sberschoolcurrencyconverter.domain.model.CurrencyEntity;
import com.example.sberschoolcurrencyconverter.domain.repository.ICurrencyRepository;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServerCurrencyRepository implements ICurrencyRepository {

    private static final String BASE_URL = "http://www.cbr.ru";
    private final ICurrencyApi mApi;
    private final CurrencyMapper mMapper;

    public ServerCurrencyRepository(@NonNull CurrencyMapper mapper) {
        mMapper = mapper;
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        // noinspection deprecation
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .build();
        mApi = retrofit.create(ICurrencyApi.class);
    }

    @NonNull
    @Override
    public List<CurrencyEntity> loadCurrencies() throws IOException {
        Call<CurrenciesData> listCall = mApi.loadCurrencies();
        Response<CurrenciesData> response = listCall.execute();
        if (response.body() == null || response.errorBody() != null) {
            throw new IOException("Не удалось загрузить список валют");
        }
        List<CurrencyData> currencies = response.body().getCurrencies();
        return mMapper.mapToEntity(currencies);
    }
}
