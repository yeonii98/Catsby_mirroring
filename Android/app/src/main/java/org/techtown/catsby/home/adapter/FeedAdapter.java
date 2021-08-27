package org.techtown.catsby.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.home.model.Feed;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.retrofit.dto.BowlCommunityUpdatePost;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.setting.MaincommentActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<Feed> itemData;
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    UserService userService = RetrofitClient.getUser();
    View view;

    Button postButton;
    EditText commentEditText;
    ImageView feedCommentButton;
    Button deleteButton ;
    Button putButton ;
    Button putFinishButton;
    EditText putText;
    TextView textView;
    Context context;
    boolean[] bool;

    public FeedAdapter(ArrayList<Feed> itemData) {
        this.itemData = itemData;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bool = new boolean[itemData.size()];
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView bowlImg;
        private TextView userName;
        private ImageView feedImg;
        private TextView content;

        private Button putButton1 = (Button)itemView.findViewById(R.id.putButton);
        private Button deleteButton1 = (Button)itemView.findViewById(R.id.deleteButton);
        private EditText putText1 = (EditText)itemView.findViewById(R.id.feed_content_EditText);
        private TextView textView1 = (TextView)itemView.findViewById(R.id.feed_content );
        private Button putFinishButton1 = (Button)itemView.findViewById(R.id.putFinishButton);


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = view.getContext();
            bowlImg = itemView.findViewById(R.id.feed_bowlImg);
            userName = itemView.findViewById(R.id.feed_username);
            feedImg = itemView.findViewById(R.id.feed_img);
            content = itemView.findViewById(R.id.feed_content);
            postButton = (Button)itemView.findViewById(R.id.post_save_button);
            commentEditText = itemView.findViewById(R.id.post_title_edit);
            feedCommentButton = (ImageView)itemView.findViewById(R.id.feed_comment);
            deleteButton = (Button)itemView.findViewById(R.id.deleteButton);
            putButton = (Button)itemView.findViewById(R.id.putButton);
            putFinishButton = (Button)itemView.findViewById(R.id.putFinishButton);
            textView = (TextView)itemView.findViewById(R.id.feed_content );

            itemView.findViewById(R.id.putButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bool[getAdapterPosition()]) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            ViewHolder.this.putButton1.setVisibility(View.INVISIBLE);
                            ViewHolder.this.putFinishButton1.setVisibility(View.VISIBLE);
                            ViewHolder.this.textView1.setVisibility(View.GONE);
                            ViewHolder.this.putText1.setVisibility(View.VISIBLE);
                            ViewHolder.this.putText1.setCursorVisible(true);
                        }
                    }
                }
            });

            itemView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteGetUser(getAdapterPosition(), itemData.get(getAdapterPosition()).getUserId(), user.getUid());
                }
            });


            itemView.findViewById(R.id.putFinishButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String putMessage1 = ViewHolder.this.putText1.getText().toString();
                    System.out.println("putMessage = " + putMessage1);

                    if (!putMessage1.equals("클릭하여 글을 작성해주세요")){
                        updateCommunity(itemData.get(getAdapterPosition()).getId(), putMessage1);
                        ViewHolder.this.textView1.setText(putMessage1);
                    }

                    ViewHolder.this.putButton1.setVisibility(View.VISIBLE);
                    ViewHolder.this.putFinishButton1.setVisibility(View.INVISIBLE);
                    ViewHolder.this.putText1.setVisibility(View.GONE);
                    ViewHolder.this.textView1.setVisibility(View.VISIBLE);
                }
            });

            feedCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadComments(itemData.get(getAdapterPosition()).getId(), getAdapterPosition());

                }
            });

            for (int i =0; i < itemData.size(); i ++) {
                UserService userService = RetrofitClient.getUser();
                Call<User> call = userService.getUser(user.getUid());
                int finalI = i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            User result = call.execute().body();
                            assert result != null;
                            if (bool[finalI] == false){
                                if (result.getId() == itemData.get(finalI).getUserId()){
                                    bool[finalI] = true;
                                }else{
                                    bool[finalI] = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_feedlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {

        EditText commentEditTextPost = view.findViewById(R.id.post_title_edit);
        Feed item = itemData.get(position);
        holder.bowlImg.setImageResource(item.getBowlImg());
        holder.userName.setText(item.getNickName());

        byte[] blob = Base64.decode(item.getImg(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(blob,0, blob.length);
        holder.feedImg.setImageBitmap(bmp);
        holder.content.setText(item.getContent());


        if (bool[position]) {
            holder.putButton1.setVisibility(View.VISIBLE);
            holder.deleteButton1.setVisibility(View.VISIBLE);
        }

        postButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String contextMessage = commentEditTextPost.getText().toString();
                postComment(user.getUid(), itemData.get(position).getId(), contextMessage);
                commentEditTextPost.setText("");
            }
        });
    }

    private void updateCommunity(int communityId, String changeTest) {
        BowlCommunityUpdatePost bowlCommunityUpdatePost = new BowlCommunityUpdatePost(changeTest);
        bowlCommunityService.updateCommunity(communityId, bowlCommunityUpdatePost).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });

    }

    private void deleteCommunity(int position, int dId){
        bowlCommunityService.deleteCommunity(dId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                itemData.remove(itemData.get(position));
                bool[position] = false;
                FeedAdapter adapter = new FeedAdapter(itemData);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRemoved(view.getVerticalScrollbarPosition());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("deleteCommunity t.getMessage() = " + t.getMessage());
            }
        });
    }

    private void deleteGetUser(int position, int communityUserId, String uid) {
        userService.getUser(uid).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User result = response.body();
                    int idByUid = result.getId();

                    if (idByUid == communityUserId){
                        deleteCommunity(position, itemData.get(position).getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });
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

    private void loadComments(long communityId, int position) {
        bowlCommunityService.getComments(communityId).enqueue(new Callback<List<BowlComment>>() {
            @Override
            public void onResponse(Call<List<BowlComment>> call, Response<List<BowlComment>> response) {
                if(response.isSuccessful()){
                    List<BowlComment> bowlComments = response.body();

                    Context context = view.getContext();
                    Intent intent = new Intent(context, MaincommentActivity.class);

                    List<BowlComment> tempComment = bowlComments;
                    List<BowlCommentUsingComment> parameterBowlCommentList= new ArrayList<>();
                    for (int i =0; i < tempComment.size(); i++){
                        BowlCommentUsingComment bowlCommentUsingComment = new BowlCommentUsingComment(tempComment.get(i).getId(), tempComment.get(i).getUser().getNickname(), tempComment.get(i).getContent(), tempComment.get(i).getCreateDate(), tempComment.get(i).getUser().getId());
                        parameterBowlCommentList.add(bowlCommentUsingComment);
                    }
                    intent.putExtra("comment", (Serializable) parameterBowlCommentList);
                    context.startActivity(intent);
            }
        }

            @Override
            public void onFailure(Call<List<BowlComment>> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
        }

    @Override
    public int getItemCount() {
        return itemData.size();
    }
}
