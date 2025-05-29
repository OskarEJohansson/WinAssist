package models.request

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastRequest(val city: String)
