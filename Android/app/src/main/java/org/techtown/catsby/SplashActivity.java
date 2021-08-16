package org.techtown.catsby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.techtown.catsby.login.LoginActivity;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        KakaoSdk.init(this, getString(R.string.kakao_native_app_key));

        Handler hd = new Handler();
        hd.postDelayed(new SplashHandler(), 3000);

    }

    private class SplashHandler implements Runnable {
        public void run() {
//            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
//                @Override
//                public Unit invoke(User user, Throwable throwable) {
//                    if (user != null) {
                        startActivity(new Intent(getApplication(), MainActivity.class));
//                    } else {
//                        startActivity(new Intent(getApplication(), LoginActivity.class));
//                    }
//                    return null;
//                }
//            });
            SplashActivity.this.finish();
        }
    }
}