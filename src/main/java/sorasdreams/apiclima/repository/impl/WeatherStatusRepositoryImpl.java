package sorasdreams.apiclima.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import sorasdreams.apiclima.client.HttpClient;
import sorasdreams.apiclima.config.ApiProperties;
import sorasdreams.apiclima.model.Language;
import sorasdreams.apiclima.repository.WeatherStatusRepository;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
public class WeatherStatusRepositoryImpl implements WeatherStatusRepository {

    private static final String PARAM_SEPARATOR = "&";

    private static final String SEARCH_ENDPOINT = "/v1/search";

    private static final String FORECAST_ENDPOINT = "/v1/forecast";
    private final HttpClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public Response getLatitudeAndLongitudeOfCity(String city, Integer count, String language) throws IOException {

        final String key = city + PARAM_SEPARATOR + count + PARAM_SEPARATOR + language;

        String responseFromRedis = getValueFromRedis(key);

        if(StringUtils.isNotBlank(responseFromRedis) && !responseFromRedis.equals("nil")){
            return objectMapper.readValue(responseFromRedis, Response.class);
        }

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

        Response r = client.doRequest(geocodingApiUrl);
        if(r.code() < 400){
            jedisPooled.setex(geocodingApiUrl, TimeUnit.SECONDS.convert(Duration.ofMinutes(10)), r.toString());
        }

        return r;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getForecastByLatAndLong(float latitude, float longitude) throws IOException {
        final String key = latitude + PARAM_SEPARATOR + longitude;
        String responseFromRedis = getValueFromRedis(key);

        if(StringUtils.isNotBlank(responseFromRedis) && !responseFromRedis.equals("nil")){
            return objectMapper.readValue(responseFromRedis, Response.class);
        }

       // RequestBody requestBody = createRequestBody(latitude, longitude);

        final String url = getEndpointWithBaseUrlAndParams(FORECAST_ENDPOINT,"latitude="
                + latitude,"longitude=" + longitude);

        Response r = client.doRequest(url);

        if(r.code() < 400){
            jedisPooled.setex(url, TimeUnit.SECONDS.convert(Duration.ofMinutes(10)), r.toString());
        }

        return r;
    }

    private String getValueFromRedis(String key){
        return jedisPooled.get(key);
    }

    private RequestBody createRequestBody(String city){
        return new FormBody.Builder().add("name",city).build();
    }

    private RequestBody createRequestBody(String city, Integer count){
        return new FormBody.Builder().add("name",city).add("count", String.valueOf(count)).build();
    }

    private RequestBody createRequestBody(String city, Integer count, String language){
        return new FormBody.Builder().add("name",city)
                .add("count", String.valueOf(count))
                .add("language", language)
                .build();
    }

    private RequestBody createRequestBody(String city, String language){
        return new FormBody.Builder().add("name",city).add("language", language)
                .build();
    }

    private RequestBody createRequestBody(float latitude, float longitude){
        return new FormBody.Builder().add("latitude", String.valueOf(latitude))
                .add("longitude", String.valueOf(longitude))
                .build();
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
