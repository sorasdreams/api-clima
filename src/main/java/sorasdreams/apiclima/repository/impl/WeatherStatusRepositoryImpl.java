package sorasdreams.apiclima.repository.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import sorasdreams.apiclima.client.HttpClient;
import sorasdreams.apiclima.config.ApiProperties;
import sorasdreams.apiclima.model.CitiesGeocodingData;
import sorasdreams.apiclima.model.Language;
import sorasdreams.apiclima.model.WeatherForecastResponse;
import sorasdreams.apiclima.repository.WeatherStatusRepository;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class WeatherStatusRepositoryImpl implements WeatherStatusRepository {

    private static final String PARAM_SEPARATOR = "&";

    private static final String SEARCH_ENDPOINT = "/v1/search";

    private static final String FORECAST_ENDPOINT = "/v1/forecast";
    private final HttpClient client;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);

    @Autowired
    private JedisPooled jedisPooled;

    @Autowired
    private ApiProperties apiProperties;

    public WeatherStatusRepositoryImpl(HttpClient client){
        this.client = client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CitiesGeocodingData> getCitiesGeocodingData(String city, Integer count, String language) throws IOException {
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

        String responseFromRedis = getValueFromRedis(geocodingApiUrl);

        if(StringUtils.isNotBlank(responseFromRedis) && !responseFromRedis.equals("nil")){
            return Optional.of(objectMapper.readValue(responseFromRedis, CitiesGeocodingData.class));
        }

        Response r = client.doRequest(geocodingApiUrl);

        if(r.body() != null && r.code() < 400){
            CitiesGeocodingData citiesGeocodingData = objectMapper.readValue(r.body().string(), CitiesGeocodingData.class);
            jedisPooled.setex(geocodingApiUrl, TimeUnit.SECONDS.convert(Duration.ofMinutes(10)), citiesGeocodingData.toString());
            return Optional.of(citiesGeocodingData);
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WeatherForecastResponse> getForecastByLatAndLong(float latitude, float longitude) throws IOException {
        final String url = getEndpointWithBaseUrlAndParams(FORECAST_ENDPOINT,"latitude="
                + latitude,"longitude=" + longitude).concat("&hourly=temperature_2m");

        String responseFromRedis = getValueFromRedis(url);

        if(StringUtils.isNotBlank(responseFromRedis) && !responseFromRedis.equals("nil")){
            return Optional.of(objectMapper.readValue(responseFromRedis, WeatherForecastResponse.class));
        }
        Response r = client.doRequest(url);

        if(r.body() != null && r.code() < 400){
            WeatherForecastResponse weatherForecastResponse = objectMapper.readValue(r.body().string(), WeatherForecastResponse.class);
            jedisPooled.setex(url, TimeUnit.SECONDS.convert(Duration.ofMinutes(10)), weatherForecastResponse.toString());
            return Optional.of(weatherForecastResponse);
        }

        return Optional.empty();
    }

    private String getValueFromRedis(String key){
        return jedisPooled.get(key);
    }

    private String getEndpointWithBaseUrlAndParams(String endpoint, String... params){

        String url = "SI ESTAS VIENDO ESTO, ESTAMOS EN PROBLEMAS";
        switch (endpoint){
            case SEARCH_ENDPOINT:
                url = apiProperties.getGeocodingApiBaseUrl().concat(endpoint);
                break;
            case FORECAST_ENDPOINT:
                url = apiProperties.getForecastApiUrl().concat(endpoint);
                break;
        }

        boolean isFirstParam = true;
        for(String param : params){
            if(isFirstParam){
                url = url.concat("?");
                isFirstParam = false;

                url = url.concat(param);
            } else {
                url = url.concat(PARAM_SEPARATOR + param);
            }
        }
        return url;
    }
}
