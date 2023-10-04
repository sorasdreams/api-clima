package sorasdreams.apiclima.repository;

import sorasdreams.apiclima.model.CitiesGeocodingData;
import sorasdreams.apiclima.model.WeatherForecastResponse;

import java.io.IOException;
import java.util.Optional;

public interface WeatherStatusRepository {
    /**
     * Searches for the given city on the weather api
     * @param city to search
     * @param count of cities if there is more than one
     * @param language to translate the result, if there is no translation returns in english
     * @return a SearchResult with data or an empty optional if no data is found
     * @throws IOException if an exception occurs during the execution
     */
    Optional<CitiesGeocodingData> getCitiesGeocodingData(String city , Integer count, String language) throws IOException;
    /**
     * Gets the forecast for the given latitude and longitude
     * @param latitude the latitude
     * @param longitude the longitude
     * @return a WeatherForecastResponse or an empty optional if no data is found
     * @throws IOException if an exception occurs during the execution
     */
    Optional<WeatherForecastResponse> getForecastByLatAndLong(float latitude, float longitude) throws IOException;
}
