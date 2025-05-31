package models.translators

import models.core.Coordinates
import models.entity.NominatimEntity
import models.entity.WeatherForecastEntity
import models.response.WeatherForecastResponse


fun WeatherForecastEntity.toResponse(city: String) = WeatherForecastResponse(
    city = city.replaceFirstChar { it.uppercase() },
    temperature = "${this.properties?.timeSeries.let { it?.firstOrNull()?.data?.instant?.details?.airTemperature }} °C",
    wind = this.properties?.timeSeries.let { it?.firstOrNull()?.data?.instant?.details?.windSpeed }.toString() + " mhs",
    description = this.properties?.timeSeries.let { it?.firstOrNull()?.data?.nextTwelveHours?.summary?.symbolCode?.split("_")?.joinToString(separator = " ", prefix = "Forecast for the next twelve hours: ") },
)

fun List<NominatimEntity>.toCoordinates(): Coordinates? {
    val first = firstOrNull() ?: return null
    val lat = first.lat?.toDoubleOrNull()
    val lon = first.lon?.toDoubleOrNull()
    if (lat == null || lon == null) {
        ("Invalid lat/lon: lat='${first.lat}', lon='${first.lon}'")
        return null
    }

    return Coordinates(lat, lon)
}