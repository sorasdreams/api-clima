package sorasdreams.apiclima.service;

import sorasdreams.apiclima.model.CityGeocodingData;
import sorasdreams.apiclima.model.WeatherForecastResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WeatherStatusService {

    /**
     * Searches for the given city on the weather api
     * @param city to search
     * @param count of cities if there is more than one
     * @param language to translate the result, if there is no translation returns in english
     * @return a SearchResponse with data or an empty optional if no data is found
     * @throws IOException if an exception occurs during the execution
     */
   List<CityGeocodingData>searchCity(String city, Integer count, String language) throws IOException;

    /**
     * Gets the forecast for the given latitude and longitude
     * @param lat the latitude
     * @param lon the longitude
     * @return a WeatherForecastResponse or an empty optional if no data is found
     * @throws IOException if an exception occurs during the execution
     */
    Optional<WeatherForecastResponse> forecastByLatAndLong(float lat, float lon) throws IOException;

}
