package com.example.dailyweatherapp.feature_weather.domain.gps

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}