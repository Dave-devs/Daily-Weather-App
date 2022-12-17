package com.example.dailyweatherapp.feature_weather.data.api

import com.example.dailyweatherapp.feature_weather.data.remote.WeatherHourly
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeathers(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
    ): WeatherHourly
}