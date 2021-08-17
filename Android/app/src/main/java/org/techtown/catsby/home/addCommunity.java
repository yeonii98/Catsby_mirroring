package org.techtown.catsby.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.dto.BowlDto;
import org.techtown.catsby.retrofit.dto.TownCommunity;
import org.techtown.catsby.retrofit.service.BowlService;
import org.techtown.catsby.retrofit.service.TownCommunityService;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addCommunity extends AppCompatActivity {
    EditText bowladdress, bowlname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_qr);

        bowlname = findViewById(R.id.idEdt);
        bowladdress = findViewById(R.id.addEdt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= bowlname.getText().toString();
                String address = bowladdress.getText().toString();

                if( name.length() > 0 && address.length() > 0) {
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    BowlService service = retrofit.create(BowlService.class);
                    BowlDto bowlDto = new BowlDto(name, address);
                    Call<Void> call = service.postBowl(bowlDto);

                    // 여기부터 ~
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String substr = sdf.format(date);

                    Intent intent = new Intent();
                    //?
                    intent.putExtra("main", (Parcelable) bowlname);
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
