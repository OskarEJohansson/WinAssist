package integration

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import models.core.Coordinates
import models.dto.NominatimDTO
import models.dto.WeatherForecastDTO
import integration.providers.KtorClientProvider


class WeatherApiClient(ktorClientProvider: KtorClientProvider) {

    val client = ktorClientProvider.client

    suspend fun fetchForecast(coordinates: Coordinates): WeatherForecastDTO {
        val response = client.get("https://api.met.no/weatherapi/locationforecast/2.0/compact") {
            url {
                contentType(ContentType.Application.Json)
                parameters.append("lat", coordinates.latitude.toString())
                parameters.append("lon", coordinates.longitude.toString())
            }
        }
        if (!response.status.isSuccess()) {
            throw BadRequestException("Weather Request could not be handled")
        }

        return response.body()
    }

    suspend fun fetchCoordinates(city: String): List<NominatimDTO> {
        val response = client.get("https://nominatim.openstreetmap.org/search") {
            url {
                contentType(ContentType.Application.Json)
                userAgent("WinAssistAssignment 1.0")
                parameters.append("q", city)
                parameters.append("format", "jsonv2")
                parameters.append("limit", "1")
            }
        }
        if (!response.status.isSuccess()) {
            throw NotFoundException("Location: $city could not be found")
        }

        val entities = try {
            response.body<List<NominatimDTO>>()
        } catch (e: Exception) {
            throw BadRequestException("Could not parse location for $city: ${e.message}")
        }

        if (entities.isEmpty()) {
            throw NotFoundException("Location: $city could not be found")
        }

        return response.body()
    }
}