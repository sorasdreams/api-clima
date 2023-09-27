package sorasdreams.apiclima.service;

import sorasdreams.apiclima.model.SearchResponse;
import sorasdreams.apiclima.model.WeatherForecastResponse;

import java.io.IOException;

public interface WeatherStatusService {

    SearchResponse searchCity(String city, Integer count, String language) throws IOException;

    WeatherForecastResponse forecastByLatAndLong(float lat, float lon) throws IOException;

}
