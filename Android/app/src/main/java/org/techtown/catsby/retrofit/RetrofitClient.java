package org.techtown.catsby.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.techtown.catsby.login.data.service.LoginService;
import org.techtown.catsby.notification.data.service.NotificationService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit getInstacne() {
        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NotificationService getNotificationService(){
        return getInstacne().create(NotificationService.class);
    }

    public static LoginService getLoginService() {
        return getInstacne().create(LoginService.class);
    }

}
