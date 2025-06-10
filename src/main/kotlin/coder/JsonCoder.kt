package coder

import kotlinx.serialization.json.Json

object JsonCoder {
    val instance: Json by lazy {
        Json {
            prettyPrint = false
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
}