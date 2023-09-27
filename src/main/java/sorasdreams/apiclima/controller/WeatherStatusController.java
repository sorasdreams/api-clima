package sorasdreams.apiclima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sorasdreams.apiclima.config.ApiProperties;
import sorasdreams.apiclima.model.Language;
import sorasdreams.apiclima.model.SearchResponse;
import sorasdreams.apiclima.model.WeatherForecastResponse;

import java.io.IOException;
import java.util.Objects;

@RestController
public class WeatherStatusController {

    static final String PARAM_SEPARATOR = "&";
    private static final Logger log = LoggerFactory.getLogger(WeatherStatusController.class);

    @Autowired
    private ApiProperties apiProperties;

    private final String API_BASE_URL;

    public WeatherStatusController(){
        API_BASE_URL = apiProperties.getApiBaseUrl();
    }

    @PostMapping( "/v1/weather/search")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> search(
            @RequestParam String city, @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String language){

        if(!city.isBlank()){

            String geocodingApiUrl = API_BASE_URL + "/v1/search?name=" + city;

            if(Objects.nonNull(count) && count > 0){
                geocodingApiUrl = geocodingApiUrl.concat(PARAM_SEPARATOR + "count=" + count);
            }
            if(Language.isValidLanguage(language)){
                geocodingApiUrl = geocodingApiUrl.concat(PARAM_SEPARATOR + "language=" + language);
            }

            //Podemos usar el cliente Okhttp
            Request geocodingRequest = new Request.Builder()
                    .url(geocodingApiUrl)
                    .build();

            //OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();
            OkHttpClient okHttpClient = new OkHttpClient();

            Call call = okHttpClient.newCall(geocodingRequest);

            //Se tiene que comunicar con la api de geocoding para obtener las coordenadas
            try(Response response = call.execute()){
                ObjectMapper objectMapper = new ObjectMapper();

                if(response.body() != null){
                  SearchResponse searchResponse = objectMapper.readValue(response.body().string(), SearchResponse.class);

                    //despues con las coordenadas tiene que consultar el clima

                  if(searchResponse.getLatitude() != 0 && searchResponse.getLongitude() != 0){
                      String weatherApiUrl = API_BASE_URL + "/v1/forecast?latitude=" + searchResponse.getLatitude()
                              + PARAM_SEPARATOR + "longitude=" + searchResponse.getLongitude();

                      Request weatherRequest = new Request.Builder()
                              .url(weatherApiUrl)
                              .build();

                      Call weatherCall = okHttpClient.newCall(weatherRequest);


                      return ResponseEntity.status(HttpStatus.OK).body(convertResponseToWeatherForecastResponse(weatherCall).toString());
                  }
                }
            } catch (IOException e){
                //TODO log
                log.error("Error al consultar la API del clima", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    private WeatherForecastResponse convertResponseToWeatherForecastResponse(Call call){

        try(Response response = call.execute()) {
            ObjectMapper objectMapper = new ObjectMapper();

            if(response.body() != null){
                return objectMapper.readValue(response.body().string()
                        , WeatherForecastResponse.class);
            }
        } catch (IOException e){
            //TODO log
            log.error("Error durante la conversion del response de endpoint forecast", e);
        }

        return new WeatherForecastResponse();
    }

}
