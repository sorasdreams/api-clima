package sorasdreams.apiclima.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;
import sorasdreams.apiclima.client.HttpClient;
import sorasdreams.apiclima.client.impl.OkHttpClientImpl;
import sorasdreams.apiclima.repository.WeatherStatusRepository;
import sorasdreams.apiclima.repository.impl.WeatherStatusRepositoryImpl;
import sorasdreams.apiclima.service.WeatherStatusService;
import sorasdreams.apiclima.service.impl.WeatherStatusServiceImpl;

@Configuration
public class ApiConfig {


    @Bean
    public HttpClient httpClient(){
        return new OkHttpClientImpl(new OkHttpClient());
    }

    @Bean
    public WeatherStatusRepository weatherStatusRepository(@Autowired HttpClient httpClient){
        return new WeatherStatusRepositoryImpl(httpClient);
    }


    @Bean
    public WeatherStatusService weatherStatusService(@Autowired WeatherStatusRepository weatherStatusRepository){
        return new WeatherStatusServiceImpl(weatherStatusRepository);
    }

    @Bean
    public JedisPooled jedisPooled(){
       return new JedisPooled("localhost", 6379);
    }

    /*
    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7380));
        return new JedisCluster(jedisClusterNodes);
    }

    */
}
