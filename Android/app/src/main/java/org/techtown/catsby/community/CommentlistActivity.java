package org.techtown.catsby.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.community.data.model.TownComment;
import org.techtown.catsby.community.data.model.TownCommunity;
import org.techtown.catsby.community.data.service.TownCommentService;
import org.techtown.catsby.community.data.service.TownCommunityService;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.setting.Comment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private cmtAdapter recyclerAdapter;
    private TownCommentService townCommentService;

    private View view;
    EditText commentlist_add;
    ImageView image_profile;
    Button commentlist_postbtn;
    Button commentlist_delete;
    Button commentlist_update;
    TextView commentlist_text;
    TextView commentlist_username;
    ImageView commentlist_image;
    TextView commentlist_date;

    List<Comments> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentlist2);

        recyclerView = findViewById(R.id.cmt_recyclerview);
        commentList = new ArrayList<>();

        recyclerAdapter = new cmtAdapter(commentList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        townCommentService = RetrofitClient.getTownCommentService();

        townCommentService.getTownComment(id).enqueue(new Callback<List<TownComment>>() {
            @Override
            public void onResponse(Call<List<TownComment>> call, Response<List<TownComment>> response) {
                if(response.isSuccessful()){
                    List<TownComment> result = response.body();
                    for(int i = 0; i < result.size(); i++){
                        recyclerAdapter.addItem(result.get(i).getContent(),result.get(i).getUser().getNickname());
                    }
                    recyclerAdapter.notifyDataSetChanged();
                }else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<TownComment>> call, Throwable t) {
                System.out.println("통신 실패" + t.getMessage());
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

    class cmtAdapter extends RecyclerView.Adapter<cmtAdapter.ItemViewHolder>{

        private List<Comments> cmtdata;

        public cmtAdapter(List<Comments> cmtdata){
            this.cmtdata = cmtdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.comment_list_item2,parent,false);
            cmtAdapter.ItemViewHolder vh = new cmtAdapter.ItemViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Comments comments = cmtdata.get(position);

            holder.commentlist_text.setText(comments.getContent());
            holder.commentlist_username.setText(comments.getNickName());
            holder.cmtImg.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return cmtdata.size();
        }

        private void addItem(String content, String nickName) {
            Comments comments = new Comments();
            comments.setContent(content);
            comments.setNickName(nickName);

            cmtdata.add(comments);
        }

        void removeItem(int position) {
            cmtdata.remove(position);
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder{
            private TextView commentlist_username;
            private TextView commentlist_text;
            private ImageView cmtImg;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                commentlist_username = itemView.findViewById(R.id.cmtNickName);
                commentlist_text = itemView.findViewById(R.id.cmtContent);
                cmtImg = itemView.findViewById(R.id.cmtImg);
            }

        }
    }
}

