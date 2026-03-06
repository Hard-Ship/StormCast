package com.example.stormcast.network;

import com.example.stormcast.ui.ForecastResponse;
import com.example.stormcast.ui.Weathermodal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeather {

    @GET("weather")
    Call<Weathermodal> getweather(@Query("q")String city,
                                  @Query("appid")String key,
                                  @Query("units")String units);

    @GET("forecast")
    Call<ForecastResponse> getForecast(@Query("q")String city,
                                       @Query("appid")String key,
                                       @Query("units")String units);
}
