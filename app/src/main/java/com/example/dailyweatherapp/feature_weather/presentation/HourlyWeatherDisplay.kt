package com.example.dailyweatherapp.feature_weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dailyweatherapp.feature_weather.domain.weather.WeatherData
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    Card(
        elevation = CardDefaults.cardElevation(),
        shape =  RoundedCornerShape(16.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Image(
                painter = painterResource(id = weatherData.weatherType.iconRes),
                contentDescription = null,
                modifier = Modifier.width(40.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${weatherData.temperatureCelsius}°C",
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}