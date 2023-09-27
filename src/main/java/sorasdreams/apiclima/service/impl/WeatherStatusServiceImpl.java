package sorasdreams.apiclima.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorasdreams.apiclima.config.ApiProperties;
import sorasdreams.apiclima.model.Language;
import sorasdreams.apiclima.model.SearchResponse;
import sorasdreams.apiclima.model.WeatherForecastResponse;
import sorasdreams.apiclima.repository.WeatherStatusRepository;
import sorasdreams.apiclima.service.WeatherStatusService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherStatusServiceImpl implements WeatherStatusService {

    private final WeatherStatusRepository weatherStatusRepository;

    private static final String PARAM_SEPARATOR = "&";

    private static final String BEGIN_OF_PARAM = "?";

    private static final String SEARCH_ENDPOINT = "/v1/search";

    private static final String FORECAST_ENDPOINT = "/v1/forecast";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ApiProperties apiProperties;

    public WeatherStatusServiceImpl(WeatherStatusRepository weatherStatusRepository){
        this.weatherStatusRepository = weatherStatusRepository;
    }

    @Override
    public SearchResponse searchCity(String city, Integer count, String language) throws IOException {
        List<String> params = new ArrayList<>();

        //La ciudad siempre va a estar
        params.add("name=" + city);

        if(Objects.nonNull(count) && count > 0){
            params.add("count=" + count);
        }
        if(Language.isValidLanguage(language)){
            params.add("language=" + language);
        }

        String geocodingApiUrl = getEndpointWithBaseUrlAndParams(SEARCH_ENDPOINT, params.toArray(new String[0])) ;


       Response response = weatherStatusRepository.getLatitudeAndLongitudeOfCity(geocodingApiUrl);
        if(response.body() != null) {


            return objectMapper.readValue(response.body().string(), SearchResponse.class);
        }

        return null;
    }

    @Override
    public WeatherForecastResponse forecastByLatAndLong(float lat, float lon) throws IOException {
        String weatherApiUrl = getEndpointWithBaseUrlAndParams(FORECAST_ENDPOINT,"latitude="
                + lat,"longitude=" + lon);

        Response responseForecast = weatherStatusRepository.getForecastByLatAndLong(weatherApiUrl);

       return objectMapper.readValue(responseForecast.body().string(), WeatherForecastResponse.class);
    }


    private String getEndpointWithBaseUrlAndParams(String endpoint, String... params){
        String url = apiProperties.getApiBaseUrl().concat(endpoint);

        if(params.length > 0){
            url = url.concat(BEGIN_OF_PARAM);

            for(String param : params){
                url = url.concat(PARAM_SEPARATOR + param);
            }
        }
        return url;
    }
}
