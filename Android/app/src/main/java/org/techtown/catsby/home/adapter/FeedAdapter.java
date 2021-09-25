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
import android.widget.Toast;

import org.techtown.catsby.R;
import org.techtown.catsby.home.model.Feed;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.retrofit.dto.BowlCommunityUpdatePost;
import org.techtown.catsby.retrofit.dto.BowlLike;
import org.techtown.catsby.retrofit.dto.BowlLikeResponse;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.home.BowlCommentActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
    Button putButton;
    Button putFinishButton;
    TextView textView;
    Context context;
    boolean[] bool;
    boolean repeat = false;

    ArrayList<Integer> likeCommunity = new ArrayList<>();
    HashMap<Integer, Integer> totalLike = new HashMap<>();
    HashMap<Integer, Integer> likeByCommunity = new HashMap<>();

    public FeedAdapter(ArrayList<Feed> itemData) {
        this.itemData = itemData;
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

        private ImageView likeButton= (ImageView)itemView.findViewById(R.id.likeButton);
        private ImageView likeFullButton= (ImageView)itemView.findViewById(R.id.likeFull);
        private TextView totalCountLike = itemView.findViewById(R.id.countLikes);

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
            textView = (TextView)itemView.findViewById(R.id.feed_content);
            EditText commentEditTextPost = view.findViewById(R.id.post_title_edit);

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

            itemView.findViewById(R.id.likeButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postLike(user.getUid(), itemData.get(getAdapterPosition()).getId());

                    ViewHolder.this.likeButton.setVisibility(View.GONE);
                    ViewHolder.this.likeFullButton.setVisibility(View.VISIBLE);
                    ViewHolder.this.totalCountLike.setText(Integer.toString(totalLike.get(itemData.get(getAdapterPosition()).getId())+1));
                }
            });

            itemView.findViewById(R.id.likeFull).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewHolder.this.likeButton.setVisibility(View.VISIBLE);
                    ViewHolder.this.likeFullButton.setVisibility(View.GONE);
                    ViewHolder.this.totalCountLike.setText(Integer.toString(totalLike.get(itemData.get(getAdapterPosition()).getId())-1));

                    int lid = likeByCommunity.get(itemData.get(ViewHolder.this.getAdapterPosition()).getId());
                    deleteLike(lid, itemData.get(ViewHolder.this.getAdapterPosition()).getId());
                    totalLike.put(itemData.get(getAdapterPosition()).getId(), totalLike.get(itemData.get(getAdapterPosition()).getId())-1);
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

            itemView.findViewById(R.id.feed_comment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadComments(itemData.get(getAdapterPosition()).getId(), getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.post_save_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String contextMessage = commentEditTextPost.getText().toString();
                    if (!contextMessage.equals("")) {
                        postComment(user.getUid(), itemData.get(getAdapterPosition()).getId(), contextMessage);
                        commentEditTextPost.setText("");
                    }else{
                        Toast.makeText(context.getApplicationContext(),"댓글을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            for (int i =0; i < itemData.size(); i++){
                loadTotalLike(itemData.get(i).getId());
            }

            for (int i =0; i < itemData.size(); i ++) {
                if (bool[i] == false){
                    if(itemData.get(i).getUid().equals(user.getUid())){
                        bool[i] = true;
                    } else{
                        bool[i] = false;
                    }
                }
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


        Feed item = itemData.get(position);
        //holder.bowlImg.setImageResource(item.getBowlImg());

        holder.userName.setText(item.getNickName());
        byte[] blob = Base64.decode(item.getImg(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(blob,0, blob.length);
        holder.feedImg.setImageBitmap(bmp);

        holder.content.setText(item.getContent());

        if (likeCommunity.size() == 0){
            bowlCommunityService.getLikes(user.getUid()).enqueue(new Callback<List<BowlLike>>() {
                @Override
                public void onResponse(Call<List<BowlLike>> call, Response<List<BowlLike>> response) {
                    if(response.isSuccessful()) {
                        List<BowlLike> bowlResult = response.body();
                        assert bowlResult != null;
                        if(likeCommunity.size() == 0){
                            for (BowlLike bowlLike : bowlResult) {
                                likeCommunity.add(bowlLike.getBowlCommunity().getId());
                                likeByCommunity.put(bowlLike.getBowlCommunity().getId(), bowlLike.getId());
                        }}
                        if (likeCommunity.contains(itemData.get(position).getId())){
                            holder.likeButton.setVisibility(View.GONE);
                            if (holder.totalCountLike.getText().equals("0")){
                                holder.totalCountLike.setText("");
                            }
                            holder.likeFullButton.setVisibility(View.VISIBLE);
                        } else{
                            holder.likeButton.setVisibility(View.VISIBLE);
                            holder.likeFullButton.setVisibility(View.GONE);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<BowlLike>> call, Throwable t) {
                    System.out.println("t.getMessage() = " + t.getMessage());
                }
            });
            repeat = true;
        }

        if (totalLike.containsKey(itemData.get(position).getId())){
            holder.totalCountLike.setText(totalLike.get(itemData.get(position).getId()).toString());
        }

        if (likeCommunity.size() > 0){
            if (likeCommunity.contains(itemData.get(position).getId())){
                holder.likeButton.setVisibility(View.GONE);
                holder.likeFullButton.setVisibility(View.VISIBLE);
            } else{
                holder.likeButton.setVisibility(View.VISIBLE);
                holder.likeFullButton.setVisibility(View.GONE);
            }
        }

        if (bool[position]) {
            holder.putButton1.setVisibility(View.VISIBLE);
            holder.deleteButton1.setVisibility(View.VISIBLE);
        } else{
            holder.putButton1.setVisibility(View.GONE);
            holder.deleteButton1.setVisibility(View.GONE);
        }
    }

    private void loadTotalLike(int communityId){
        bowlCommunityService.getTotalLikes(communityId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    Integer count = response.body();
                    if (!totalLike.containsKey(communityId)){
                        totalLike.put(communityId, count);

                    }
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
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

    private void deleteLike(int deleteLikeId, int communityId){
        bowlCommunityService.deleteLike(deleteLikeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                likeByCommunity.remove(communityId);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void deleteCommunity(int position, int deleteId){
        bowlCommunityService.deleteCommunity(deleteId).enqueue(new Callback<Void>() {
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

    private void postLike(String uid, int communityId){
        bowlCommunityService.saveLike(uid, communityId).enqueue(new Callback<BowlLikeResponse>() {
            @Override
            public void onResponse(Call<BowlLikeResponse> call, Response<BowlLikeResponse> response) {
                likeCommunity.add(communityId);
                likeByCommunity.put(communityId, response.body().getId());
                totalLike.put(communityId, totalLike.get(communityId)+1);
            }

            @Override
            public void onFailure(Call<BowlLikeResponse> call, Throwable t) {
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
                    Intent intent = new Intent(context, BowlCommentActivity.class);

                    List<BowlComment> tempComment = bowlComments;
                    List<BowlCommentUsingComment> parameterBowlCommentList= new ArrayList<>();
                    for (int i =0; i < tempComment.size(); i++){
                        BowlCommentUsingComment bowlCommentUsingComment = new BowlCommentUsingComment(tempComment.get(i).getId(), tempComment.get(i).getUser().getNickname(), tempComment.get(i).getContent(), tempComment.get(i).getCreateDate(), tempComment.get(i).getUser().getId(), tempComment.get(i).getUid());
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
