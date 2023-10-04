package sorasdreams.apiclima.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("snd")
@PropertySource(value = {"classpath:application-${environment}.properties"})
public class ApiProperties implements InitializingBean, DisposableBean {

    @Value("${geocoding.api.baseurl}")
    private String geocodingApiBaseUrl;

    @Value("${forecast.api.baseurl}")
    private String forecastApiUrl;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public String getGeocodingApiBaseUrl() {
        return geocodingApiBaseUrl;
    }

    public String getForecastApiUrl() {
        return forecastApiUrl;
    }
}
