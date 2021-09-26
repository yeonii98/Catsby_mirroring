package org.techtown.catsby.cattown;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.dto.CatProfile;
import org.techtown.catsby.retrofit.service.CatService;
import org.techtown.catsby.util.ImageUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatTownDetailActivity extends AppCompatActivity  {

    private TextView catInfo;
    private TextView catGender;
    private TextView area;
    private TextView health;
    private TextView feature;
    private ImageView catimgview;
    private CheckBox yesNeuter;
    private CheckBox noNeuter;
    private CheckBox unknownNeuter;
    private Bitmap bm;

    List<CatProfile> catList;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattown_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("우리동네 고양이 상세보기");

        Intent intent = getIntent();
        String linkedid = intent.getStringExtra("linkedid");
        System.out.println(linkedid);
        int linkedid1 = Integer.parseInt(linkedid);

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://15.164.36.183:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CatService retrofitService = retrofit.create(CatService.class);
        Call<CatProfile> call = retrofitService.getCatProfile(linkedid1);
        call.enqueue(new Callback<CatProfile>() {


            @Override
            public void onResponse(Call<CatProfile> call, Response<CatProfile> response) {
                if (response.isSuccessful()) {
                    CatProfile result = response.body();
                    //고양이 이름
                    catInfo.setText(result.getCatName());
                    //고양이 성별
                    if(result.getGender()==2){
                        catGender.setText("수컷");
                    }else if(result.getGender()==1){
                        catGender.setText("암컷");
                    } else {
                        catGender.setText("성별 모름");
                    }
                    //고양이 활동 지역
                    area.setText(result.getAddress());
                    //고양이 중성화 여부
                    if(result.getSpayed()==2){
                        noNeuter.setChecked(true);
                        yesNeuter.setChecked(false);
                        unknownNeuter.setChecked(false);
                    } else if(result.getSpayed()==1) {
                        noNeuter.setChecked(false);
                        yesNeuter.setChecked(true);
                        unknownNeuter.setChecked(false);
                    } else {
                        noNeuter.setChecked(false);
                        yesNeuter.setChecked(false);
                        unknownNeuter.setChecked(true);
                    }
                    //고양이 건강 상태
                    health.setText(result.getHealth());
                    //고양이 특징
                    feature.setText(result.getContent());
                    //고양이 사진
                    if(result.getImage() != null){
                        bm = ImageUtils.makeBitMap(result.getImage());
                        catimgview.setImageBitmap(bm);}
                    else{
                        bm = null;}

                } else{
                    System.out.println("실패");
                }

            }

            @Override
            public void onFailure(Call<CatProfile> call, Throwable t) {
                System.out.println("통신 실패");
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catInfo = (TextView) findViewById(R.id.tv_catinfo);
        catGender = (TextView) findViewById (R.id.tv_catgender);
        area = (TextView) findViewById(R.id.tv_area);
        health = (TextView) findViewById(R.id.tv_health);
        feature = (TextView) findViewById(R.id.tv_feature);
        yesNeuter = (CheckBox) findViewById(R.id.cb_yesneuter);
        unknownNeuter = (CheckBox) findViewById(R.id.cb_unknownneuter);
        noNeuter = (CheckBox) findViewById(R.id.cb_noneuter);
        catimgview = (ImageView)findViewById(R.id.CatDetailImageView);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}

////////////////////////////////////////////image slider 코드////////////////////////////////////////

    /*
    private String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_960_720.jpg",
            "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_960_720.jpg"
    };

     */


        /*
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this,images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);

         */

    /*
    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16,8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this, R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this, R.drawable.bg_indicator_inactive
                ));
            }
        }
    }
     */
