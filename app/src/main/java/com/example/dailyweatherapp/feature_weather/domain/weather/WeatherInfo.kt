package com.example.dailyweatherapp.feature_weather.domain.weather

data class WeatherInfo(
    //It represents the lists of weather data on daily basis(7days).
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    //It gets the weather for daily and current weather forecast.
    val currentWeatherData: WeatherData?
)