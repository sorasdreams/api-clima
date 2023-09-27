package sorasdreams.apiclima.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application-${enviroment}.properties"})
public class ApiProperties implements InitializingBean, DisposableBean {

    @Value("weather.api.baseurl:https://geocoding-api.open-meteo.com")
    private String apiBaseUrl;


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }
}
