package com.example.dailyweatherapp.feature_weather.data.weather_mappers

import com.example.dailyweatherapp.feature_weather.data.remote.WeatherHoulyData
import com.example.dailyweatherapp.feature_weather.data.remote.WeatherHourly
import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherData
import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherInfo
import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    //Index for day of week.
    val index: Int,
    val data: WeatherData
)

//Mapper function for days of the week to the values in the WeatherData.
fun WeatherHoulyData.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    //Return map time to each weather info with local day timestamp.
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }
}

//Mapper function for hour of the day(get the weather for the hour in the day).
fun WeatherHourly.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}