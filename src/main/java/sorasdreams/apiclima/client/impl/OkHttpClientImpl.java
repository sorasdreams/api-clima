package sorasdreams.apiclima.client.impl;


import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sorasdreams.apiclima.client.HttpClient;

import java.io.IOException;

public class OkHttpClientImpl implements HttpClient {


    private final OkHttpClient okHttpClient;

    public OkHttpClientImpl(OkHttpClient client){
        this.okHttpClient = client;
    }

    /**
     * Ejecuta un request
     * @param url
     * @return
     * @throws IOException
     */
    @Override
    public Response doRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);

        return call.execute();
    }

}
