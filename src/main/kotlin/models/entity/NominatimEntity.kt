package models.entity

import kotlinx.serialization.Serializable

@Serializable
data class NominatimEntity(
    val lat: String? = null,
    val lon: String? = null)