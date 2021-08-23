package org.techtown.catsby.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import org.techtown.catsby.R;
import org.techtown.catsby.community.Comments;
import org.techtown.catsby.community.TownCommentListActivity;
import org.techtown.catsby.community.data.service.TownCommentService;

import java.util.ArrayList;
import java.util.List;

public class MaincommentActivity extends AppCompatActivity {

    private RecyclerView mainrecyclerView;

    List<Comments> maincommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincomment);

        mainrecyclerView = findViewById(R.id.maincmt_recyclerview);
        maincommentList = new ArrayList<>();

        //뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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