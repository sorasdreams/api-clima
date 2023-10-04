package sorasdreams.apiclima.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import sorasdreams.apiclima.model.SearchResponse;
import sorasdreams.apiclima.model.SearchResult;
import sorasdreams.apiclima.model.WeatherForecastResponse;
import sorasdreams.apiclima.repository.WeatherStatusRepository;
import sorasdreams.apiclima.service.WeatherStatusService;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherStatusServiceImpl implements WeatherStatusService {

    private final WeatherStatusRepository weatherStatusRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public WeatherStatusServiceImpl(WeatherStatusRepository weatherStatusRepository){
        this.weatherStatusRepository = weatherStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SearchResponse> searchCity(String city, Integer count, String language) throws IOException {

       Response response = weatherStatusRepository.getLatitudeAndLongitudeOfCity(city, count, language);
        if(response.body() != null) {
          SearchResult searchResult = objectMapper.readValue(response.body().string(), SearchResult.class);

          if(Objects.isNull(count)){
              return Optional.of(searchResult.getResults()[0]);
          }
          //Ver de devolver la lista y en caso que no se haya pasado el count solo devolver la lista con el primer valor

          if(count > 0){

          }
          for(SearchResponse searchResponse : searchResult.getResults()){
              //Devolvemos el primer resultado

          }
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WeatherForecastResponse> forecastByLatAndLong(float lat, float lon) throws IOException {
        Response responseForecast = weatherStatusRepository.getForecastByLatAndLong(lat, lon);

        if(responseForecast.body() != null){
            return Optional.of(objectMapper.readValue(responseForecast.body().string(), WeatherForecastResponse.class));
        }
        return Optional.empty();
    }
}
