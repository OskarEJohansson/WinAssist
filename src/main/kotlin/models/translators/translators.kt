package models.translators

import models.core.Coordinates
import models.dto.NominatimDTO
import models.dto.WeatherForecastDTO
import models.response.WeatherForecastResponse
import org.slf4j.LoggerFactory


private val LOG = LoggerFactory.getLogger(List::class.java)

fun WeatherForecastDTO.toResponse(city: String) = WeatherForecastResponse(
    city = city.replaceFirstChar { it.uppercase() },
    temperature = "${this.properties?.timeSeries.let { it?.firstOrNull()?.data?.instant?.details?.airTemperature }} °C",
    wind = this.properties?.timeSeries.let { it?.firstOrNull()?.data?.instant?.details?.windSpeed }.toString() + " mhs",
    description = this.properties?.timeSeries.let { it?.firstOrNull()?.data?.nextTwelveHours?.summary?.symbolCode?.split("_")?.joinToString(separator = " ", prefix = "Forecast for the next twelve hours: ") },
)

fun List<NominatimDTO>.toDomain(): Coordinates? {
    val first = firstOrNull() ?: return null
    val lat = first.lat?.toDoubleOrNull()
    val lon = first.lon?.toDoubleOrNull()
    if (lat == null || lon == null) {
        LOG.error("Invalid lat/lon: lat='${first.lat}', lon='${first.lon}'")
        return null
    }

    return Coordinates(lat, lon)
}