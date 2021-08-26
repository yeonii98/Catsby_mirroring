package org.techtown.catsby.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.techtown.catsby.R;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;

import java.util.ArrayList;
import java.util.List;

public class MaincommentActivity extends AppCompatActivity {
    List<BowlCommentUsingComment> mainCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincomment);
        //maincommentList = new ArrayList<>();

        //뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        mainCommentList = (List<BowlCommentUsingComment>) intent.getSerializableExtra("comment");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MainCommentAdapter mainCommentAdapter = new MainCommentAdapter(mainCommentList);
        RecyclerView BowlCommunityRecyclerView = findViewById(R.id.maincmt_recyclerview);
        BowlCommunityRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        BowlCommunityRecyclerView.setAdapter(mainCommentAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}