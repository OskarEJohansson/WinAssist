# Weather Forecast API

## Overview

This project is a Ktor-based backend application that provides a simple weather forecast for a given city. It fetches weather data from the MET Norway Locationforecast API and presents a summarized version. To enhance performance and reduce reliance on external APIs, the application implements a caching layer using Redis.

## Prerequisites

Before you begin, ensure you have the following installed:

*   **Kotlin**: Version 2.1.20 is recommended.
*   **Java Development Kit (JDK)**: Version 23 is recommended.
*   **Gradle**: For building the project (often bundled with IntelliJ IDEA or can be installed separately).
*   **Docker**: Required if you want to run a local Redis instance for caching (recommended for development).

### Running Redis Locally (for Caching)

The application uses Redis for caching API responses. For local development, you can easily run a Redis instance using Docker:
1.  Pull the Redis image (if you haven't already):
    docker pull redis

2.  Run the Redis container:
    docker run -d --name dev-redis -p 6379:6379 redis

This command starts a Redis container named `dev-redis` in detached mode (`-d`) and maps port `6379` of the container to port `6379` on your host machine. The application is configured to connect to Redis on `localhost:6379` by default (see `network.providers.RedisClientProvider`).

    You can check if the container is running with `docker ps`. To stop the container, use `docker stop dev-redis`.

### Building the Project

1.  Clone the repository (if applicable):
    git clone <your-repository-url>
    cd <your-project-directory>
    (Replace <your-repository-url> and <your-project-directory> with your actual repository URL and project directory name.)

2.  Build the project using Gradle:
    You can use the Gradle wrapper included in the project.
    -   On macOS/Linux:
        ./gradlew build
    -   On Windows:
        gradlew.bat build
        This command will compile the source code, run tests (if any), and package the application.

### Running the Project

After successfully building the project, you can run the application.

1.  Using Gradle:
    The simplest way to run the application during development is using the Gradle `run` task:
    -   On macOS/Linux:
        ./gradlew run
    -   On Windows:
        gradlew.bat run
        By default, the server will start on http://0.0.0.0:8080.

2.  Running the JAR file (after building):
    If you have built a JAR file (usually found in `build/libs/`), you can run it directly:
    java -jar build/libs/<your-jar-file-name>.jar
    (Replace <your-jar-file-name>.jar with the actual name of your generated JAR file.)

* ## Managing Dependencies (using Version Catalog)

This project uses a [Gradle Version Catalog](https://docs.gradle.org/current/userguide/version_catalog_basics.html) to manage dependencies, which is defined in the `gradle/libs.versions.toml` file. This approach helps in centralizing dependency versions and makes it easier to update them.

### Adding a New Dependency

To add a new library or dependency to the project:

1.  **Declare the Dependency in `gradle/libs.versions.toml`:**
    Open the `gradle/libs.versions.toml` file. You'll see sections like `[versions]`, `[libraries]`, and `[plugins]`.
    **Add Version (if new):** If the library's version isn't already defined, add it to the `[versions]` section.

### Caching with Redis
To enhance performance and reduce reliance on external APIs, this application implements a caching layer using Redis.
•Purpose: Responses from the Nominatim API (for city coordinates) and the MET Norway API (for weather forecasts) are cached.
•Mechanism: The service.RedisCacheService handles storing and retrieving data from Redis. Objects are serialized to JSON strings (using kotlinx.serialization) before being cached and deserialized upon retrieval. The WeatherForecastService and its internal WeatherHelperService utilize this for caching.
•Cached Data:
    •City coordinates (from Nominatim) are cached using a key derived from the city name (namespace: coordinates).
    •Weather forecasts (from MET Norway) are cached using a key derived from the city name (namespace: weatherForecast).
•Cache Duration: Cached items typically have a Time-To-Live (TTL) of 10 minutes (600 seconds), after which they expire, and fresh data will be fetched from the external APIs upon the next request.
•Benefits:
    •Faster response times for frequently requested cities.
    •Reduced number of calls to external services, helping to stay within API rate limits and reducing external dependencies.

## Response
ResponseThe API will respond with a JSON object containing the following fields:
* city (String): The name of the city for which the forecast was requested (capitalized).
* •temperature (String, nullable): The current air temperature (e.g., "14.0 °C"). Can be "N/A" if data is unavailable.
* •wind (String, nullable): The current wind speed (e.g., "1.7 m/s"). Can be "N/A" if data is unavailable.
* •description (String, nullable): A textual description of the forecast for the next twelve hours (e.g., "Forecast for the next twelve hours: Partlycloudy day").

## Extending the API Response
If you wish to add more fields to the API response (e.g., humidity, pressure, 
different forecast periods), you'll generally need to follow these steps:

1. Identify the Data in WeatherForecastEntity.kt: The models.entity.WeatherForecastEntity class (and its nested classes) represents the full data structure fetched from the external MET Norway API. Locate the specific data point you want to include (e.g., relativeHumidity within WeatherForecastEntity.Details). Ensure this data is being correctly deserialized from the API (check @SerialName annotations and nullability).
2. Add the Field to WeatherForecastResponse.kt: Modify the models.response.WeatherForecastResponse data class to include your new field with the desired name and type.
3. Update the Translator in translators.kt: The models.translators.translators.kt file contains the extension function WeatherForecastEntity.toResponse(). You need to update this function to:
    * Extract the desired data from the WeatherForecastEntity instance.
    * Format it if necessary (e.g., adding units, converting to a string).
    * Assign it to the new field in the WeatherForecastResponse object you are constructing.

By following these steps, you can customize the API response to include more detailed weather information as needed.