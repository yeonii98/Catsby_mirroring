package org.techtown.catsby.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import static org.techtown.catsby.home.adapter.FeedAdapter.MComment;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommentResponse;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.home.adapter.BowlCommentAdapter;
import org.techtown.catsby.retrofit.service.BowlCommunityService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BowlCommentActivity extends AppCompatActivity {
    List<BowlCommentUsingComment> mainCommentList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    BowlCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincomment);
        EditText textPost = findViewById(R.id.post_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("댓글");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        mainCommentList = MComment;
        adapter = new BowlCommentAdapter(mainCommentList);

        int communityId = intent.getExtras().getInt("communityId");
        findViewById(R.id.post_save_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contextMessage = textPost.getText().toString();
                if (!contextMessage.equals("")) {
                    postComment(user.getUid(), communityId, contextMessage);
                    textPost.setText("");
                    Toast.makeText(getApplicationContext().getApplicationContext(),"댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    RecyclerView BowlCommunityRecyclerView = findViewById(R.id.maincmt_recyclerview);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext().getApplicationContext(),"댓글을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RecyclerView BowlCommunityRecyclerView = findViewById(R.id.maincmt_recyclerview);
        BowlCommunityRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        BowlCommunityRecyclerView.setAdapter(adapter);
    }

    private void postComment(String uid, int id, String context) {
        BowlCommentPost bowlCommentPost = new BowlCommentPost(uid, id, context);
        bowlCommunityService.saveComment(uid, id, bowlCommentPost).enqueue(new Callback<BowlCommentResponse>() {
            @Override
            public void onResponse(Call<BowlCommentResponse> call, Response<BowlCommentResponse> response) {
                BowlCommentResponse bowlCommentResponse = (BowlCommentResponse) response.body();
                BowlCommentUsingComment bowlCommentUsingComment = new BowlCommentUsingComment(bowlCommentResponse.getId(), bowlCommentResponse.getNickname(), context, bowlCommentResponse.getDate(), bowlCommentResponse.getUserId(), user.getUid(), id);
                adapter.addItem((BowlCommentUsingComment) bowlCommentUsingComment);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BowlCommentResponse> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}