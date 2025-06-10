package models.dto

import kotlinx.serialization.Serializable

@Serializable
data class NominatimDTO(
    val lat: String? = null,
    val lon: String? = null)
