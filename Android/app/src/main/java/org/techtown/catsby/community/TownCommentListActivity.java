package org.techtown.catsby.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.firebase.auth.FirebaseAuth;

import org.techtown.catsby.R;
import org.techtown.catsby.community.data.model.TownComment;
import org.techtown.catsby.community.data.service.TownCommentService;
import org.techtown.catsby.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TownCommentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private cmtAdapter recyclerAdapter;

    String uid = FirebaseAuth.getInstance().getUid();

    TownCommentService townCommentService = RetrofitClient.getTownCommentService();

    List<Comments> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town_commentlist);

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

        townCommentService.getTownComment(id).enqueue(new Callback<List<TownComment>>() {
            @Override
            public void onResponse(Call<List<TownComment>> call, Response<List<TownComment>> response) {
                if(response.isSuccessful()){
                    List<TownComment> result = response.body();
                    for(int i = 0; i < result.size(); i++){
                        recyclerAdapter.addItem(result.get(i).getId(),result.get(i).getUser().getUid(),result.get(i).getContent(),result.get(i).getUser().getNickname(), result.get(i).getDate());
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

    private void putTownComment(int id, TownComment townComment){
        townCommentService.putTownComment(id,townComment).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    //정상적으로 통신이 성공된 경우
                    System.out.println("댓글 수정 성공");
                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void deleteTownComment(int id){
        townCommentService.deleteTownComment(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    System.out.println("삭제 성공");
                } else {
                    System.out.println("실패");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("통신 실패!");
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

            View view = inflater.inflate(R.layout.town_comment_list_item,parent,false);
            cmtAdapter.ItemViewHolder vh = new cmtAdapter.ItemViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Comments comments = cmtdata.get(position);

            holder.commentlist_text.setText(comments.getContent());
            holder.commentlist_username.setText(comments.getNickName());
            holder.commentlist_date.setText(comments.getDate());

            if(!uid.equals(comments.getUid())){
                holder.cmtUpdateBtn.setVisibility(View.GONE);
                holder.cmtDeleteBtn.setVisibility(View.GONE);
            }

            holder.cmtUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.cmtUpdateBtn.getText().equals("수정")){
                        holder.commentlist_text.setVisibility(View.GONE);
                        holder.editCmtContent.setText(holder.commentlist_text.getText());
                        holder.editCmtContent.setVisibility(View.VISIBLE);
                        holder.cmtUpdateBtn.setText("완료");
                    }
                    else if(holder.cmtUpdateBtn.getText().equals("완료")){
                        holder.commentlist_text.setText(holder.editCmtContent.getText());
                        holder.commentlist_text.setVisibility(View.VISIBLE);
                        holder.editCmtContent.setVisibility(View.GONE);
                        holder.cmtUpdateBtn.setText("수정");
                        TownComment townComment = new TownComment(holder.editCmtContent.getText().toString());
                        putTownComment(comments.getId(),townComment);
                    }
                }
            });

            holder.cmtDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(TownCommentListActivity.this);
                    ad.setTitle("댓글 삭제");
                    ad.setMessage("해당 댓글을 삭제하시겠습니까?");

                    ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeItem(position);
                            notifyItemRemoved(position);
                            deleteTownComment(comments.getId());
                        }
                    });

                    ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cmtdata.size();
        }

        private void addItem(int id, String uid, String content, String nickName, String date) {
            Comments comments = new Comments();
            comments.setId(id);
            comments.setUid(uid);
            comments.setContent(content);
            comments.setNickName(nickName);
            comments.setDate(date);

            cmtdata.add(comments);
        }

        void removeItem(int position) {
            cmtdata.remove(position);
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder{
            private TextView commentlist_username;
            private TextView commentlist_text;
            private EditText editCmtContent;
            private ImageView cmtImg;
            private TextView commentlist_date;
            private Button cmtUpdateBtn;
            private Button cmtDeleteBtn;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                commentlist_username = itemView.findViewById(R.id.cmtNickName);
                commentlist_text = itemView.findViewById(R.id.cmtContent);
                editCmtContent = itemView.findViewById(R.id.editCmtContent);
                cmtImg = itemView.findViewById(R.id.user_img);
                commentlist_date = itemView.findViewById(R.id.commentlist_date);
                cmtUpdateBtn = itemView.findViewById(R.id.cmtUpdateBtn);
                cmtDeleteBtn = itemView.findViewById(R.id.cmtDeleteBtn);
            }

        }
    }

}
