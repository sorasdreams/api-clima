package sorasdreams.apiclima.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sorasdreams.apiclima.model.SearchResponse;
import sorasdreams.apiclima.model.WeatherForecastResponse;
import sorasdreams.apiclima.service.WeatherStatusService;

import java.io.IOException;

@RestController
public class WeatherStatusController {

    private static final Logger log = LoggerFactory.getLogger(WeatherStatusController.class);

    @Autowired
    private WeatherStatusService weatherStatusService;

    @PostMapping( "/v1/weather/search")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> search(
            @RequestParam String city, @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String language){

        if(!city.isBlank()){
            //Se tiene que comunicar con la api de geocoding para obtener las coordenadas
            try {
                SearchResponse searchResponse = weatherStatusService.searchCity(city, count, language);

                if (searchResponse != null && searchResponse.getLatitude() != 0 && searchResponse.getLongitude() != 0) {
                    //despues con las coordenadas tiene que consultar el clima
                    WeatherForecastResponse weatherForecastResponse = weatherStatusService
                            .forecastByLatAndLong(searchResponse.getLatitude(),searchResponse.getLongitude());

                    return ResponseEntity.status(HttpStatus.OK).body(weatherForecastResponse.toString());
                }
            } catch (IOException e){
                log.error("Error al consultar la API del clima", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
