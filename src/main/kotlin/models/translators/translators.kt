package models.translators

import models.entity.WeatherForecastEntity
import models.response.WeatherForecastResponse


fun WeatherForecastEntity.toResponse(city: String) = WeatherForecastResponse(
    city = city.replaceFirstChar { it.uppercase() },
    temperature = "${this.properties?.timeSeries.let { it?.firstOrNull()?.data?.instant?.details?.airTemperature }} °C",
    wind = this.properties?.timeSeries.let { it?.firstOrNull()?.data?.instant?.details?.windSpeed }.toString() + " mhs",
    description = this.properties?.timeSeries.let { it?.firstOrNull()?.data?.nextTwelveHours?.summary?.symbolCode?.split("_")?.joinToString(separator = " ", prefix = "Forecast for the next twelve hours: ") },
)