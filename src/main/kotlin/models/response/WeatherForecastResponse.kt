package models.response

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastResponse(
    val city: String,
    val temperature: String?,
    val wind: String?,
    val description: String?)
