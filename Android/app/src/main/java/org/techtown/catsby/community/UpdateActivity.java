package org.techtown.catsby.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.community.data.model.TownCommunity;
import org.techtown.catsby.community.data.service.TownCommunityService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private TownCommunityService townCommunityService;
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

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        int id = intent.getIntExtra("id",0);
        int position = intent.getIntExtra("position",0);


        edtTitle.setText(title);
        edtContent.setText(content);

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if(title.length() > 0 && content.length() > 0) {
                    TownCommunity townCommunity = new TownCommunity(title,content);
                    townCommunityService = RetrofitClient.getTownCommunityService();
                    townCommunityService.putTown(id, townCommunity).enqueue(new Callback<Void>() {
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

//                    Date date = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//                    String substr = sdf.format(date);

                    Intent intent = new Intent(UpdateActivity.this, FragmentCommunity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("position",position);
                    setResult(0, intent);


                    finish();
                }

            }
        });
    }
}