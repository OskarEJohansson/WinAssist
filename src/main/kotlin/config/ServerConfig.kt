package config

import coder.JsonCoder
import controller.WeatherController
import integration.WeatherApiClient
import integration.providers.KtorClientProvider
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import routes.weatherApiRoutes
import service.RedisCacheService
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

    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            call.request.path().startsWith("/api/v1")
        }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            "Status: $status, HTTP method: $httpMethod, User agent: $userAgent"
        }
    }

    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respondText(text = "400: ${cause.message}", status = HttpStatusCode.BadRequest)
        }
        exception<NotFoundException> { call, cause ->
            call.respondText(text = "404: ${cause.message}", status = HttpStatusCode.NotFound)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        weatherApiRoutes(WeatherController(
            weatherForecastService = WeatherForecastService(
                RedisCacheService,
                JsonCoder.instance,
            WeatherApiClient(KtorClientProvider)
            )))
    }
}