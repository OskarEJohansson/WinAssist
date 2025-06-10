package service

import io.ktor.server.plugins.*
import kotlinx.serialization.json.Json
import models.core.Coordinates
import models.dto.NominatimDTO
import models.dto.WeatherForecastDTO
import models.translators.toDomain
import integration.WeatherApiClient
import utils.findCache
import utils.setCache


class WeatherForecastService(jedis: RedisCacheService,coder: Json, api: WeatherApiClient) {

    private val helperService = WeatherHelperService(jedis, coder, api)

    suspend fun getWeatherForecast(city: String): WeatherForecastDTO {
        val coordinates: Coordinates = helperService.getLocation(city)
        return helperService.getWeather(city, coordinates)
    }

    class WeatherHelperService(private val jedis: RedisCacheService, private val coder: Json, private val api: WeatherApiClient) {
        companion object {
            private const val COORDINATES_NAMESPACE = "coordinates"
            private const val WEATHER_NAMESPACE = "weatherForecast"
        }
                suspend fun getLocation(city: String): Coordinates {
                    findCoordinatesInCache(COORDINATES_NAMESPACE, city)?.let { return it }
                    val coordinates = api.fetchCoordinates(city)
                    jedis.setCache(coder, COORDINATES_NAMESPACE, city, coordinates,)

                    return coordinates.toDomain() ?: throw NotFoundException("Location: $city coordinates could not be found")
                }

                suspend fun getWeather(city: String, coordinates: Coordinates): WeatherForecastDTO {
                    findWeatherForecastInCache(WEATHER_NAMESPACE, city)?.let { return it }
                    val weather = api.fetchForecast(coordinates)

                    jedis.setCache(coder, WEATHER_NAMESPACE, city, weather)
                    return weather
                }

                fun findCoordinatesInCache(namespace: String, city: String): Coordinates? =
                    jedis.findCache<List<NominatimDTO>, Coordinates?>(coder, namespace, city) { it.toDomain() }

                fun findWeatherForecastInCache(namespace: String, city: String): WeatherForecastDTO? = jedis.findCache(coder, namespace, city)
            }

}