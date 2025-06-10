package models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastDTO(
    val type: String?,
    val geometry: Geometry?,
    val properties: Properties?)
{
    @Serializable
    data class Geometry(
        val type: String?,
        val coordinates: List<Double?>
    )
    @Serializable
    data class Properties(
        val meta: Meta?,
        @SerialName("timeseries")
        val timeSeries: List<TimeSeries?>?, )

    @Serializable
    data class Meta(
        @SerialName("updated_at")
        val updatedAt: String?,
        val units: Units?)

    @Serializable
    data class Units(
        @SerialName("air_pressure_at_sea_level")
        val airPressureAtSeaLevel: String? = null,
        @SerialName("air_temperature")
        val airTemperature: String? = null,
        @SerialName("air_temperature_max")
        val airTemperatureMax: String? = null,
        @SerialName("air_temperature_min")
        val airTemperatureMin: String? = null,
        @SerialName("cloud_area_fraction")
        val cloudAreaFraction: String? = null,
        @SerialName("cloud_area_fraction_high")
        val cloudAreaFractionHigh: String? = null,
        @SerialName("cloud_area_fraction_low")
        val cloudAreaFractionLow: String? = null,
        @SerialName("cloud_area_fraction_medium")
        val cloudAreaFractionMedium: String? = null,
        @SerialName("dew_point_temperature")
        val dewPointTemperature: String? = null,
        @SerialName("fog_area_fraction")
        val fogAreaFraction: String? = null,
        @SerialName("precipitation_amount")
        val precipitationAmount: String? = null,
        @SerialName("precipitation_amount_max")
        val precipitationAmountMax: String? = null,
        @SerialName("precipitation_amount_min")
        val precipitationAmountMin: String? = null,
        @SerialName("probability_of_precipitation")
        val probabilityOfPrecipitation: String? = null,
        @SerialName("probability_of_thunder")
        val probabilityOfThunder: String? = null,
        @SerialName("relative_humidity")
        val relativeHumidity: String? = null,
        @SerialName("ultraviolet_index_clear_sky")
        val ultravioletIndexClearSky: String? = null,
        @SerialName("wind_from_direction")
        val windFromDirection: String? = null,
        @SerialName("wind_speed")
        val windSpeed: String? = null,
        @SerialName("wind_speed_of_gust")
        val windSpeedOfGust: String? = null
    )

    @Serializable
    data class TimeSeries(
        val time: String?,
        val data: Data? = null)

    @Serializable
    data class Data(
        val instant: Instant?,
        @SerialName("next_12_hours")
        val nextTwelveHours: NextTwelveHours? = null,
        @SerialName("next_1_hours")
        val nextOneHours: NextOneHours? = null,
        @SerialName("next_6_hours")
        val nextSixHours: NextSixHours? = null)

    @Serializable
    data class Instant(val details: Details?)

    @Serializable
    data class Details(
        @SerialName("air_pressure_at_sea_level")
        val airPressureAtSeaLevel: Double? = null,
        @SerialName("air_temperature")
        val airTemperature: Double? = null,
        @SerialName("cloud_area_fraction")
        val cloudAreaFraction: Double? = null,
        @SerialName("cloud_area_fraction_high")
        val cloudAreaFractionHigh: Double? = null,
        @SerialName("cloud_area_fraction_low")
        val cloudAreaFractionLow: Double? = null,
        @SerialName("cloud_area_fraction_medium")
        val cloudAreaFractionMedium: Double? = null,
        @SerialName("dew_point_temperature")
        val dewPointTemperature: Double? = null,
        @SerialName("fog_area_fraction")
        val fogAreaFraction: Double? = null,
        @SerialName("relative_humidity")
        val relativeHumidity: Double? = null,
        @SerialName("ultraviolet_index_clear_sky")
        val ultravioletIndexClearSky: Double? = null,
        @SerialName("wind_from_direction")
        val windFromDirection: Double? = null,
        @SerialName("wind_speed")
        val windSpeed: Double? = null,
        @SerialName("wind_speed_of_gust")
        val windSpeedOfGust: Double? = null)

    @Serializable
    data class Summary(
        @SerialName("symbol_code")
        val symbolCode: String?)

    @Serializable
    data class NextTwelveHours(
        val summary: Summary?,
        val details: Details?)

    @Serializable
    data class NextOneHours(
        val summary: Summary?,
        val details: Details?)

    @Serializable
    data class NextSixHours(
        val summary: Summary?,
        val details: Details?)
}