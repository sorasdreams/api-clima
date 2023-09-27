package sorasdreams.apiclima.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPooled;
import sorasdreams.apiclima.client.HttpClient;
import sorasdreams.apiclima.repository.WeatherStatusRepository;

import java.io.IOException;

@Repository
public class WeatherStatusRepositoryImpl implements WeatherStatusRepository {


    private final HttpClient client;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JedisPooled jedisPooled;

   // @Autowired
    //private JedisCluster jedisCluster;

    public WeatherStatusRepositoryImpl(HttpClient client){
        this.client = client;
    }

    @Override
    public Response getLatitudeAndLongitudeOfCity(String url) throws IOException {
        String responseFromRedis = getValueFromRedis(url);

        if(StringUtils.isNotBlank(responseFromRedis) && !responseFromRedis.equals("nil")){
            return objectMapper.readValue(responseFromRedis, Response.class);
        }
        Response r = client.doRequest(url);

        jedisPooled.set(url,r.toString());
        return r;
    }

    @Override
    public Response getForecastByLatAndLong(String url) throws IOException {
        String responseFromRedis = getValueFromRedis(url);

        if(StringUtils.isNotBlank(responseFromRedis) && !responseFromRedis.equals("nil")){
            return objectMapper.readValue(responseFromRedis, Response.class);
        }
        Response r = client.doRequest(url);

        jedisPooled.set(url,r.toString());

        return r;
    }

    private String getValueFromRedis(String key){
        return jedisPooled.get(key);
    }

}
