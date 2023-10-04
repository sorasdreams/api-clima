package sorasdreams.apiclima.service.impl;

import org.springframework.stereotype.Service;
import sorasdreams.apiclima.model.CityGeocodingData;
import sorasdreams.apiclima.model.CitiesGeocodingData;
import sorasdreams.apiclima.model.WeatherForecastResponse;
import sorasdreams.apiclima.repository.WeatherStatusRepository;
import sorasdreams.apiclima.service.WeatherStatusService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherStatusServiceImpl implements WeatherStatusService {

    private final WeatherStatusRepository weatherStatusRepository;


    public WeatherStatusServiceImpl(WeatherStatusRepository weatherStatusRepository){
        this.weatherStatusRepository = weatherStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CityGeocodingData> searchCity(String city, Integer count, String language) throws IOException {
        boolean hasCount =Objects.nonNull(count);

        if(hasCount && count == 0){
            throw new IllegalArgumentException("La cantidad de resultados a mostrar no puede ser 0");
        }

       Optional<CitiesGeocodingData> citiesGeocodingDataOptional = weatherStatusRepository.getCitiesGeocodingData(city, count, language);
        if(citiesGeocodingDataOptional.isPresent()) {
            CitiesGeocodingData citiesGeocodingData = citiesGeocodingDataOptional.get();

          //Si no hay cantidad a mostrar entonces solo devuelvo el primer valor si lo hubiera
          if(!hasCount && citiesGeocodingData.getResults().length > 0){
            return Collections.singletonList(citiesGeocodingData.getResults()[0]);
          } else if (hasCount && citiesGeocodingData.getResults().length == 0){
              return new ArrayList<>();
          } else if (hasCount && citiesGeocodingData.getResults().length > 0){
              return getResultsToShowByCount(citiesGeocodingData.getResults(),count);
          }
        }

        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WeatherForecastResponse> forecastByLatAndLong(float lat, float lon) throws IOException {
        return weatherStatusRepository.getForecastByLatAndLong(lat, lon);
    }

    private List<CityGeocodingData> getResultsToShowByCount(CityGeocodingData[] searchRespons, Integer count){
        if(count > searchRespons.length){
            throw new ArrayIndexOutOfBoundsException("La cantidad de resultados a mostrar no puede ser mayor al total de resultados que devolvio la API");
        }
        return Arrays.asList(searchRespons).subList(0,count);
    }
}
