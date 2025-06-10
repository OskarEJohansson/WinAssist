package utils

import kotlinx.serialization.json.Json
import service.RedisCacheService

inline fun <reified T, R> RedisCacheService.findCache(coder:Json, namespace: String, city: String, transform: (T) -> R): R?{
    val json = this.get(namespace, city) ?: return null
    val decoded: T = coder.decodeFromString(json)
    return transform(decoded)
}

inline fun <reified T> RedisCacheService.findCache(coder:Json, namespace: String, city: String): T?{
    val json = this.get(namespace, city) ?: return null
    return coder.decodeFromString(json)
}

inline fun <reified T> RedisCacheService.setCache(coder:Json, namespace: String, city:String, result: T) {
    val json = coder.encodeToString(result)
    this.set(namespace, city, json)
}