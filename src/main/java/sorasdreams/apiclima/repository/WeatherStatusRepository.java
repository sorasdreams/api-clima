package sorasdreams.apiclima.repository;

import okhttp3.Response;

import java.io.IOException;

public interface WeatherStatusRepository {

    Response getLatitudeAndLongitudeOfCity(String url) throws IOException;
    Response getForecastByLatAndLong(String url) throws IOException;
}
