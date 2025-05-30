Weather Forecast API

## Overview
This project is a Ktor-based backend application that provides a simple weather forecast for a given city. It fetches weather data from the MET Norway Locationforecast API and presents a summarized version.

## Prerequisites
Before you begin, ensure you have the following installed:

*   **Kotlin**: Version 2.1.20 is recommended.
*   **Java Development Kit (JDK)**:- Version 23 is recommended.
*   Gradle - For building the project (often bundled with IntelliJ IDEA or can be installed separately).

## Building the Project

1.  **Clone the repository (if applicable):**

2.  **Build the project using Gradle:**
    You can use the Gradle wrapper included in the project.
    *   On macOS/Linux:

* ## Managing Dependencies (using Version Catalog)

This project uses a [Gradle Version Catalog](https://docs.gradle.org/current/userguide/version_catalog_basics.html) to manage dependencies, which is defined in the `gradle/libs.versions.toml` file. This approach helps in centralizing dependency versions and makes it easier to update them.

### Adding a New Dependency

To add a new library or dependency to the project:

1.  **Declare the Dependency in `gradle/libs.versions.toml`:**
    Open the `gradle/libs.versions.toml` file. You'll see sections like `[versions]`, `[libraries]`, and `[plugins]`.
    *   **Add Version (if new):** If the library's version isn't already defined, add it to the `[versions]` section.

## API Flow
1. **City Input**: The user provides a city name to the API.
2. **Geocoding**: The application uses OpenStreetMap's Nominatim API to convert the city name into geographic coordinates (latitude and longitude).
3. **Weather Fetch**: The coordinates are used to query the MET Norway (yr.no) Locationforecast API for current weather data.
4. **Response Formatting**: The raw weather data is parsed and formatted into a simplified, user-friendly JSON structure and returned to the client.

## Response
The API will respond with a JSON object containing the following fields:
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

## Implementation Status
✅ Geocoding via OpenStreetMap Nominatim  
✅ Weather data integration with MET Norway Locationforecast API
✅ Custom response formatting with temperature, wind, and weather description  
✅ Basic error handling for failed or empty API results  
🟡 Caching (not implemented)  
🟡 Unit tests (not implemented)  

## Reflections
This project was completed as part of a coding challenge with a focus on integrating third-party APIs and delivering clean, readable Kotlin code. 
Although I am a junior developer, I aimed to follow professional standards, clear project structuring, and preparation for modularization using Gradle's version catalog.

Given the time constraints, I prioritized functionality and clarity over architectural complexity. If more time were available, I would add caching, unit testing, and consider modular separation. 
I look forward to discussing these choices during the interview.
