package service

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import kotlinx.serialization.SerializationException
import models.core.Coordinates
import models.entity.NominatimEntity
import models.entity.WeatherForecastEntity
import network.KtorClientProvider


class WeatherForecastService(nominateUrl: String ) {

    private val helperService = WeatherHelperService(nominateUrl)

    suspend fun getWeatherForecast(city: String): WeatherForecastEntity {
        val coordinates: Coordinates = helperService.getLocation(city)
        return helperService.getWeather(coordinates)
    }

    class WeatherHelperService(private val nominateUrl: String){
        suspend fun getLocation(city: String): Coordinates {

           return try {
               val result = KtorClientProvider.client.get(nominateUrl){
                   url {
                       contentType(ContentType.Application.Json)
                       userAgent("WinAssistAssignment 1.0")
                       parameters.append("q", city)
                       parameters.append("format", "jsonv2")
                       parameters.append("limit", "1")
                   }
               }

               if(result.status.isSuccess()){
                   val result = result.body<List<NominatimEntity>>()
                   Coordinates(result[0].lat?.toDouble(), result[0].lon?.toDouble())

               } else {
                   throw NotFoundException("Location: $city could not be found")
               }

           } catch (e: Exception) {
               throw BadRequestException("Request to fetch weather data could not be handled: ${e.message} ")
           }
        }

        suspend fun getWeather(coordinates: Coordinates): WeatherForecastEntity {
            try {
                val result = KtorClientProvider.client.get("https://api.met.no/weatherapi/locationforecast/2.0/compact"){
                        url {
                            contentType(ContentType.Application.Json)
                            parameters.append("lat", coordinates.latitude.toString())
                            parameters.append("lon", coordinates.longitude.toString())
                        }
                    }

                if(result.status.isSuccess()){
                    return result.body<WeatherForecastEntity>()

                } else {
                    throw BadRequestException("Weather Request could not be handled")
                }

            } catch (e: Exception) {
                throw SerializationException("Weather Request could not be serialized: ${e.message}")
            }
        }
    }
}



