package org.techtown.catsby.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.techtown.catsby.community.data.service.TownLikeService;
import org.techtown.catsby.qrcode.data.service.QRBowlService;
import org.techtown.catsby.login.data.service.LoginService;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;
import org.techtown.catsby.retrofit.service.CatService;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.setting.FragmentsetMyLoc;
import org.techtown.catsby.setting.data.service.MyWritingService;
import org.techtown.catsby.community.data.service.TownCommentService;
import org.techtown.catsby.community.data.service.TownCommunityService;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL =  "http://15.164.36.183:8080/";

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NotificationService getNotificationService() {
        return getInstance().create(NotificationService.class);
    }

    public static LoginService getLoginService() {
        return getInstance().create(LoginService.class);
    }

    public static BowlService getBowlService() {
        return getInstance().create(BowlService.class);
    }

    public static BowlCommunityService getBowlCommunityService() {
        return getInstance().create(BowlCommunityService.class);
    }

    public static UserService getUser() {
        return getInstance().create(UserService.class);
    }

    public static TownCommunityService getTownCommunityService() {
        return getInstance().create(TownCommunityService.class);
    }

    public static MyWritingService getMyWritingService() {
        return getInstance().create(MyWritingService.class);
    }

    public static TownCommentService getTownCommentService() {
        return getInstance().create(TownCommentService.class);
    }

    public static TownLikeService getTownLikeService() {
        return getInstance().create(TownLikeService.class);
    }

    public static QRBowlService getQrBowlService() {
        return getInstance().create(QRBowlService.class);
    }

    public static CatService catService() {
        return getInstance().create(CatService.class);
    }

}