package controller

import io.ktor.server.plugins.BadRequestException
import models.request.WeatherForecastRequest
import models.response.WeatherForecastResponse
import models.translators.toResponse

import service.WeatherForecastService

class WeatherController(private val weatherForecastService: WeatherForecastService) {

    suspend fun getWeatherForecast(request: WeatherForecastRequest): WeatherForecastResponse {
        if (request.city.isEmpty()) {
            throw BadRequestException("City name cannot be empty")
        }
        return weatherForecastService.getWeatherForecast(request.city).toResponse(request.city)
    }
}