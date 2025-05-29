package config

import kotlinx.serialization.json.Json
import models.core.Secrets
import java.io.File

/**
 * ONLY FOR DEVELOPMENT
 * If API Kay is needed, ad a secret.json file in the resources' folder.
 * Configure the Secrets model to correspond with the keys that are needed in the project.
 * Inject the file in ServerConfig routing to use in API - calls
 */

class Config {
    companion object SecretsLoader {
        private val secrets =  File("src/main/resources/secrets.json")
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
        fun init(): Secrets = json.decodeFromString<Secrets>(secrets.readText())
    }
}