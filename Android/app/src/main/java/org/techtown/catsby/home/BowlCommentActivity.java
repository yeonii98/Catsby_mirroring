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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincomment);
        EditText textPost = findViewById(R.id.post_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("댓글");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        //mainCommentList = (List<BowlCommentUsingComment>) intent.getSerializableExtra("comment");
        mainCommentList = MComment;

        int communityId = intent.getExtras().getInt("communityId");

        findViewById(R.id.post_save_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contextMessage = textPost.getText().toString();
                if (!contextMessage.equals("")) {
                    postComment(user.getUid(), communityId, contextMessage);
                    textPost.setText("");
                }else{
                    Toast.makeText(getApplicationContext().getApplicationContext(),"댓글을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                loadComments(communityId);
            }
        });

        BowlCommentAdapter mainCommentAdapter = new BowlCommentAdapter(mainCommentList);
        RecyclerView BowlCommunityRecyclerView = findViewById(R.id.maincmt_recyclerview);
        BowlCommunityRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        BowlCommunityRecyclerView.setAdapter(mainCommentAdapter);
    }

    private void postComment(String uid, int id, String context) {
        BowlCommentPost bowlCommentPost = new BowlCommentPost(uid, id, context);
        bowlCommunityService.saveComment(uid, id, bowlCommentPost).enqueue(new Callback<List<BowlComment>>() {
            @Override
            public void onResponse(Call<List<BowlComment>> call, Response<List<BowlComment>> response) {
                System.out.println("save success");
            }

            @Override
            public void onFailure(Call<List<BowlComment>> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });
    }

    private void loadComments(long communityId) {
        bowlCommunityService.getComments(communityId).enqueue(new Callback<List<BowlComment>>() {
            @Override
            public void onResponse(Call<List<BowlComment>> call, Response<List<BowlComment>> response) {
                if(response.isSuccessful()){
                    List<BowlComment> bowlComments = response.body();

                    List<BowlComment> tempComment = bowlComments;
                    List<BowlCommentUsingComment> parameterBowlCommentList= new ArrayList<>();
                    for (int i =0; i < tempComment.size(); i++){
                        BowlCommentUsingComment bowlCommentUsingComment = new BowlCommentUsingComment(tempComment.get(i).getId(), tempComment.get(i).getUser().getNickname(), tempComment.get(i).getContent(), tempComment.get(i).getCreateDate(), tempComment.get(i).getUser().getId(), tempComment.get(i).getUid(), (int) communityId);
                        parameterBowlCommentList.add(bowlCommentUsingComment);
                    }

                    mainCommentList = parameterBowlCommentList;
                    MComment = parameterBowlCommentList;
                    BowlCommentAdapter bowlCommentAdapter = new BowlCommentAdapter(mainCommentList);
                    bowlCommentAdapter.notifyDataSetChanged();
                    bowlCommentAdapter.notifyItemInserted(getCurrentFocus().getVerticalScrollbarPosition());
                    recreate();
                }
            }

            @Override
            public void onFailure(Call<List<BowlComment>> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
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