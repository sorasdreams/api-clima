package sorasdreams.apiclima.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sorasdreams.apiclima.model.Language;
import sorasdreams.apiclima.model.CityGeocodingData;
import sorasdreams.apiclima.model.WeatherForecastResponse;
import sorasdreams.apiclima.service.WeatherStatusService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class WeatherStatusController {

    private static final Logger log = LoggerFactory.getLogger(WeatherStatusController.class);

    @Autowired
    private WeatherStatusService weatherStatusService;

    @PostMapping( "/v1/forecast")
    @ResponseStatus(code = HttpStatus.OK)
    public WeatherForecastResponse forecast(
            @RequestParam String city){

        if(StringUtils.isBlank(city)){
            throw new IllegalArgumentException("La ciudad no puede ser un espacio en blanco");
        }

        //Se tiene que comunicar con la api de geocoding para obtener las coordenadas
        try {
            List<CityGeocodingData> cityGeocodingDataList = geocoding(city, 1, Language.ES.name());

            CityGeocodingData geocodingData = cityGeocodingDataList.get(0);

            if (geocodingData.getLatitude() != 0 && geocodingData.getLongitude() != 0) {
                //despues con las coordenadas tiene que consultar el clima
                Optional<WeatherForecastResponse> weatherForecastResponse = weatherStatusService
                        .forecastByLatAndLong(geocodingData.getLatitude(), geocodingData.getLongitude());
                if(weatherForecastResponse.isPresent()){
                    return weatherForecastResponse.get();
                }
            }
        } catch (IOException e){
            log.error("Error al consultar la API del clima", e);
        }

        return new WeatherForecastResponse();
    }

    @PostMapping( "/v1/geocoding")
    @ResponseStatus(code = HttpStatus.OK)
    public List<CityGeocodingData> geocoding(@RequestParam String city
            , @RequestParam(required = false) Integer count,
                                       @RequestParam(required = false) String language) throws IOException {
            List<CityGeocodingData> cityGeocodingDataList = weatherStatusService.searchCity(city, count, language);

            if(!cityGeocodingDataList.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(cityGeocodingDataList).getBody();
            }

        return new ArrayList<>();
    }

}
