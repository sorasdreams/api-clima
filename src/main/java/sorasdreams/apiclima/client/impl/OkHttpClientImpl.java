package sorasdreams.apiclima.client.impl;


import okhttp3.*;
import sorasdreams.apiclima.client.HttpClient;

import java.io.IOException;

public class OkHttpClientImpl implements HttpClient {


    private final OkHttpClient okHttpClient;

    public OkHttpClientImpl(OkHttpClient client){
        this.okHttpClient = client;
    }

    /**
     * Ejecuta un request
     * @param url to use for the request
     * @param requestBody post request body
     * @return request response
     * @throws IOException thrown if an error occur during the execution of this request
     */
    @Override
    public Response doRequest(String url, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);

        return call.execute();
    }

    @Override
    public Response doRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);

        return call.execute();
    }

}
