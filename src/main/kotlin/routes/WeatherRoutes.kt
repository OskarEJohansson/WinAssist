package routes

import controller.WeatherController
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import models.response.WeatherForecastResponse

fun Route.weatherApiRoutes(weatherController: WeatherController) {
    route("/api/v1/weather") {
        get("/forecast/{city}") {
            val city = call.parameters["city"] ?: throw BadRequestException("Missing city")
            val forecastResponse = weatherController.getWeatherForecast(city)
            call.respond(message = forecastResponse, typeInfo<WeatherForecastResponse>())
        }
    }
}