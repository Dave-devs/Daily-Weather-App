package com.example.dailyweatherapp.feature_weather.data.repository

import com.example.dailyweatherapp.feature_weather.data.api.WeatherApi
import com.example.dailyweatherapp.feature_weather.data.weather_mappers.toWeatherInfo
import com.example.dailyweatherapp.feature_weather.domain.repository.WeatherRepository
import com.example.dailyweatherapp.feature_weather.domain.util.Resource
import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(latitude: Double, longitude: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeathers(
                    latitude = latitude,
                    longitude = longitude
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error occurred.")
        }
    }
}