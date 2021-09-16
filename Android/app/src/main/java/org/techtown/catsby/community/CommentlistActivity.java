package org.techtown.catsby.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentUpdate;
import org.techtown.catsby.retrofit.service.BowlCommunityService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CommentlistActivity extends AppCompatActivity {

    Button commentUpdate;
    TextView commentText;
    TextView userName;
    Button commentDelete;
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentlist);

        EditText commentlist_add;
        ImageView image_profile;
        Button commentlist_postbtn;
        commentDelete = (Button)findViewById(R.id.commentlist_delete);

        ImageView commentlist_image;
        TextView commentlist_date;

        commentUpdate = (Button)findViewById(R.id.commentlist_update);
        commentText = (TextView)findViewById(R.id.commentlist_text);;
        userName = (TextView)findViewById(R.id.commentlist_username);;

        //뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        List<BowlComment> item = (List<BowlComment>) intent.getSerializableExtra("comment");

        if (item.size() > 0){
            System.out.println("item = " + item.get(0).getContent());
            commentText.setText(item.get(0).getContent());
            userName.setText(item.get(0).getContent());
        }

        commentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String putMessage = commentText.getText().toString();
                putComment(item.get(0).getId(), putMessage);

            }
        });

        commentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment(item.get(0).getId());
            }
        });

    }

    private void deleteComment(int id) {
        bowlCommunityService.deleteComment(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(" success ");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void putComment(int id, String putMessage) {
        BowlCommentUpdate bowlCommentUpdate = new BowlCommentUpdate(putMessage);
        bowlCommunityService.putComment(id, bowlCommentUpdate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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

    /*
    @NonNull
    @Override
    public FragmentCommunity.RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_list_item,
                viewGroup, false);
        return new FragmentCommunity.RecyclerAdapter.ItemViewHolder(view);
    } */

}