package com.example.dailyweatherapp.feature_weather.domain.repository

import com.example.dailyweatherapp.feature_weather.domain.util.Resource
import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(latitude: Double, longitude: Double): Resource<WeatherInfo>
}