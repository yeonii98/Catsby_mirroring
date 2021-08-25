package org.techtown.catsby.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.techtown.catsby.community.data.service.TownLikeService;
import org.techtown.catsby.qrcode.data.service.QRBowlService;
import org.techtown.catsby.login.data.service.LoginService;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.setting.data.service.MyWritingService;
import org.techtown.catsby.community.data.service.TownCommentService;
import org.techtown.catsby.community.data.service.TownCommunityService;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL =  "http://15.164.36.183:8080/";

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

    public static NotificationService getNotificationService() {
        return getInstacne().create(NotificationService.class);
    }

    public static LoginService getLoginService() {
        return getInstacne().create(LoginService.class);
    }

    public static BowlService getBowlService() {
        return getInstacne().create(BowlService.class);
    }

    public static BowlCommunityService getBowlCommunityService() {
        return getInstacne().create(BowlCommunityService.class);
    }

    public static UserService getUser() {
        return getInstacne().create(UserService.class);
    }

    public static TownCommunityService getTownCommunityService() {
        return getInstacne().create(TownCommunityService.class);
    }

    public static MyWritingService getMyWritingService() {
        return getInstacne().create(MyWritingService.class);
    }

    public static TownCommentService getTownCommentService() {
        return getInstacne().create(TownCommentService.class);
    }

    public static TownLikeService getTownLikeService() {
        return getInstacne().create(TownLikeService.class);
    }

    public static QRBowlService getQrBowlService() {
        return getInstacne().create(QRBowlService.class);
    }

}