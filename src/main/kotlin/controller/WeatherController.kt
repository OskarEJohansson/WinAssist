package controller

import models.response.WeatherForecastResponse
import models.translators.toResponse
import service.WeatherForecastService

class WeatherController(private val weatherForecastService: WeatherForecastService) {

    suspend fun getWeatherForecast(city: String): WeatherForecastResponse {
        return weatherForecastService.getWeatherForecast(city).toResponse(city)
    }
}