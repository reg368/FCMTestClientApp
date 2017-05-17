package james.com.fcmtest;


import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by A4037 on 2017/5/12.
 */
public class OKHttp {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = null;
    public OKHttp() {
        client = new OkHttpClient();
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String post(String url, String key ,String value) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add(key, value)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }



}
