package org.techtown.catsby.cattown;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.community.Memo;
import org.techtown.catsby.retrofit.dto.CatProfile;
import org.techtown.catsby.retrofit.dto.TownCommunity;
import org.techtown.catsby.retrofit.service.CatService;
import org.techtown.catsby.retrofit.service.TownCommunityService;
import org.w3c.dom.Text;

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

public class CatTownDetailActivity extends AppCompatActivity {

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    private TextView catInfo;
    private TextView catAge;
    private TextView catGender;
    private TextView careUser;
    private TextView area;
    private TextView health;
    private TextView feature;

    private CheckBox yesNeuter;
    private CheckBox noNeuter;

    private Button editBtn;

    List<CatProfile> catList;

    private String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_960_720.jpg",
            "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_960_720.jpg"
    };

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattown_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //붙여넣기 시작

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatService retrofitService = retrofit.create(CatService.class);
        Call<CatProfile> call = retrofitService.getCatProfile(1);
        call.enqueue(new Callback<CatProfile>() {


            @Override
            public void onResponse(Call<CatProfile> call, Response<CatProfile> response) {
                if (response.isSuccessful()) {
                    CatProfile result = response.body();
                    //고양이 이름
                    catInfo.setText(result.getCatName());
                    //고양이 성별
                    if(result.getGender()==false){
                        catGender.setText("수컷");
                    }else if(result.getGender()==true){
                        catGender.setText("암컷");
                    } else {
                        catGender.setText("성별 모름");
                    }
                    //고양이 활동 지역
                    area.setText(result.getAddress());
                    //고양이 중성화 여부
                    if(result.getSpayed()==false){
                        noNeuter.setChecked(true);
                        yesNeuter.setChecked(false);
                    } else if(result.getSpayed()==true) {
                        noNeuter.setChecked(false);
                        yesNeuter.setChecked(true);
                    } else {
                        noNeuter.setChecked(false);
                        yesNeuter.setChecked(false);
                    }
                    //고양이 건강 상태
                    health.setText(result.getHealth());
                    //고양이 특징
                    feature.setText(result.getContent());

                } else{
                    System.out.println("실패");
                }

            }

            @Override
            public void onFailure(Call<CatProfile> call, Throwable t) {
                System.out.println("통신 실패");
            }
        });
            /*
            @Override
            public void onResponse(Call<CatProfile> call, Response<CatProfile> response) {
                if(response.isSuccessful()){
                    CatProfile cat = response.body();
                    catInfo.setText(cat.getCatName());


                }
                else {
                    System.out.println("실패");
                }

            }

            @Override
            public void onFailure(Call<CatProfile> call, Throwable t) {
                System.out.println("통신 실패!");
            }

        });

             */
            /*
            @Override
            public void onResponse(Call<List<CatProfile>> call, Response<List<CatProfile>> response) {
                if(response.isSuccessful()){
                    //정상적으로 통신이 성공된 경우
                    List<CatProfile> result = response.body();

                    for(int i = 0; i < result.size(); i++){
                        CatProfile catProfile = new CatProfile(result.get(i).getCatName(),result.get(i).getHealth(),
                                result.get(i).getAddress(), result.get(i).getGender(), result.get(i).getImage(),
                                result.get(i).getContent(), result.get(i).getSpayed());

                        catInfo.setText(result.get(i).getCatName().toString());
                        area.setText(result.get(i).getAddress().toString());
                        health.setText(result.get(i).getHealth().toString());
                        feature.setText(result.get(i).getContent().toString());
                        if(result.get(i).getSpayed().equals(0)){
                            noNeuter.setChecked(true);
                        }else {
                            noNeuter.setChecked(false);
                        }
                    }
                    //recyclerAdapter.notifyDataSetChanged();
                    //여기까지 완료 인텔리제이 서버 켜놓고 시험해봐야됨
                    //나는 캣 프로필 목록(get)과 캣 프로필 클릭했을 때(get), 그리고 캣 프로필 만드는 것(put?post) 까지 세가지 구현해야함
                    //지금것은 캣 프로필 클릭 했을 때 임
                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<CatProfile>> call, Throwable t) {
                System.out.println("통신 실패!");
            }
            });

            //붙여넣기 끝

             */


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String catName = intent.getStringExtra("id");

        catInfo = (TextView) findViewById(R.id.tv_catinfo);
        catAge = (TextView) findViewById(R.id.tv_catage);
        catGender = (TextView) findViewById (R.id.tv_catgender);
        careUser = (TextView) findViewById(R.id.tv_careuser);
        area = (TextView) findViewById(R.id.tv_area);
        health = (TextView) findViewById(R.id.tv_health);
        feature = (TextView) findViewById(R.id.tv_feature);
        yesNeuter = (CheckBox) findViewById(R.id.cb_yesneuter);
        noNeuter = (CheckBox) findViewById(R.id.cb_noneuter);

        //catInfo.setText(catName + " ( 5세 / 수컷 )");
        //careUser.setText(catName + "를 챙겨주는 유저는 N명 입니다");
        //area.setText("고양이가 활동하는 지역을 적어주세요");
        //health.setText("고양이 건강 상태를 적어주세요");
        //feature.setText("고양이 특징을 적어주세요");
        //yesNeuter.setChecked(true);

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

//        editBtn = (Button) findViewById(R.id.btn_cattown_edit);
//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CatTownEditActivity.class);
//                startActivity(intent);
//            }
//        });
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
}
