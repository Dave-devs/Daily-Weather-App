package com.example.dailyweatherapp.feature_weather.presentation

import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)