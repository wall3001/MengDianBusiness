package mengdian.business.app.utils;

import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 验证码
 * Created by wall on 2018/7/25 11:07
 * w_ll@winning.com.cn
 */
public class VerificationCode {


    public void sendCode(String phone,String msg) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account","N6052046");
        jsonObject.addProperty("password","bGuoT4hswa742a");
        jsonObject.addProperty("msg",msg);
        jsonObject.addProperty("phone",phone);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://smssh1.253.com/msg/send/json")
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("code=", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("code", response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("code", headers.name(i) + ":" + headers.value(i));
                }
                Log.d("code", "onResponse: " + response.body().string());
            }
        });
    }


}
