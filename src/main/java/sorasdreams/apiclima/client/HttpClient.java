package sorasdreams.apiclima.client;

import okhttp3.Response;

import java.io.IOException;

public interface HttpClient {

    Response doRequest(String url) throws IOException;
}
