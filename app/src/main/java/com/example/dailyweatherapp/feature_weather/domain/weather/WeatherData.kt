package com.example.dailyweatherapp.feature_weather.domain.weather

import java.time.LocalDateTime
//Specific data for given hour on daily basis.
data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType
)