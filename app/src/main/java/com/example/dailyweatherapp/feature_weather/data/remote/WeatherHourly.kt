package com.example.dailyweatherapp.feature_weather.data.remote

import com.squareup.moshi.Json

data class WeatherHourly(
    @field:Json(name = "hourly")
    val weatherData: WeatherHoulyData
)