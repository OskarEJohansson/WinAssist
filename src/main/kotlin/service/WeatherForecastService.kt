package service

import io.ktor.server.plugins.*
import kotlinx.serialization.json.Json
import models.core.Coordinates
import models.entity.NominatimEntity
import models.entity.WeatherForecastEntity
import models.translators.toCoordinates
import network.WeatherApiClient
import utils.findCache
import utils.setCache


class WeatherForecastService(private val jedis: RedisCacheService, private val coder: Json, private val api: WeatherApiClient) {

    private val helperService = WeatherHelperService(jedis, coder, api)

    suspend fun getWeatherForecast(city: String): WeatherForecastEntity {
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
            val entities = api.fetchCoordinates(city)
            jedis.setCache(coder, COORDINATES_NAMESPACE, city, entities,)
            return entities.toCoordinates() ?: throw NotFoundException("Location: $city could not be serialized")
        }

        suspend fun getWeather(city: String, coordinates: Coordinates): WeatherForecastEntity {
            findWeatherForecastInCache(WEATHER_NAMESPACE, city)?.let { return it }
            val weather = api.fetchForecast(coordinates)
            jedis.setCache(coder, WEATHER_NAMESPACE, city, weather)
            return weather
        }

        fun findCoordinatesInCache(namespace: String, city: String): Coordinates? =
            jedis.findCache<List<NominatimEntity>, Coordinates?>(coder, namespace, city) { it.toCoordinates() }

        fun findWeatherForecastInCache(namespace: String, city: String): WeatherForecastEntity? =
            jedis.findCache(coder, namespace, city)
    }
}




