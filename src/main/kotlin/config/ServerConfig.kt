package config

import controller.WeatherController
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import routes.weatherApiRoutes
import service.WeatherForecastService

fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }

        exception<BadRequestException> { call, cause ->
            call.respondText(text = "400: ${cause.message}", status = HttpStatusCode.BadRequest)
        }

        exception<NotFoundException> { call, cause ->
            call.respondText(text = "404: ${cause.message}", status = HttpStatusCode.NotFound)
        }
    }

    routing {
        weatherApiRoutes(WeatherController(weatherForecastService = WeatherForecastService("https://nominatim.openstreetmap.org/search")))
    }
}