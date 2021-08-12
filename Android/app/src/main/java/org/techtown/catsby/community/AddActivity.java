package org.techtown.catsby.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.dto.TownCommunity;
import org.techtown.catsby.retrofit.service.TownCommunityService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity {

    EditText edtTitle,edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if(title.length() > 0 && content.length() > 0) {
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    TownCommunityService service = retrofit.create(TownCommunityService.class);
                    TownCommunity townCommunity = new TownCommunity(title,content);

                    Call<Void> call = service.postTown(townCommunity);

                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String substr = sdf.format(date);

                    Intent intent = new Intent();
                    intent.putExtra("main", title);
                    intent.putExtra("sub", substr);
                    setResult(0, intent);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                //정상적으로 통신이 성공된 경우

                                System.out.println("성공");
                            } else {
                                System.out.println("실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("통신 실패 : " + t.getMessage());
                        }
                    });


                    finish();
                }

            }
        });
    }
}