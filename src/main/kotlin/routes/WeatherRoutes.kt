package routes

import controller.WeatherController
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import models.request.WeatherForecastRequest
import models.response.WeatherForecastResponse

fun Route.weatherApiRoutes(weatherController: WeatherController){
    route("/api/v1/weather") {
        get("/forecast") {
            val request = call.receive<WeatherForecastRequest>()
            val forecastResponse = weatherController.getWeatherForecast(request)
            call.respond(message = forecastResponse, typeInfo<WeatherForecastResponse>())
        }
    }
}