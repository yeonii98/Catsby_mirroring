package org.techtown.catsby.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
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
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.retrofit.dto.BowlCommunityUpdatePost;
import org.techtown.catsby.retrofit.dto.BowlLike;
import org.techtown.catsby.retrofit.dto.BowlLikeList;
import org.techtown.catsby.retrofit.dto.BowlLikeResponse;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.home.BowlCommentActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
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
    private Bitmap bm = null;

    Button postButton;
    EditText commentEditText;
    ImageView feedCommentButton;
    Button deleteButton ;
    Button putButton;
    Button putFinishButton;
    TextView textView;
    Context context;
    boolean[] bool;
    static boolean repeat = false;

    public static List<BowlCommentUsingComment> MComment;
    static HashMap<Integer, Integer> likeCommunity = new HashMap<>();
    static HashMap<Integer, Integer> totalLike = new HashMap<>();
    static HashMap<Integer, Integer> likeByCommunity = new HashMap<>();

    public FeedAdapter(ArrayList<Feed> itemData) {
        this.itemData = itemData;
        bool = new boolean[itemData.size()];
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImg;
        private TextView userName;
        private ImageView feedImg;
        private TextView content;

        private Button itemViewPutButton = (Button)itemView.findViewById(R.id.putButton);
        private Button itemViewDeleteButton = (Button)itemView.findViewById(R.id.deleteButton);
        private EditText itemViewPutText = (EditText)itemView.findViewById(R.id.feed_content_EditText);
        private TextView itemViewTextView = (TextView)itemView.findViewById(R.id.feed_content );
        private Button itemViewPutFinishButton = (Button)itemView.findViewById(R.id.putFinishButton);
        private TextView dateView = (TextView)itemView.findViewById(R.id.date);

        private ImageView likeButton= (ImageView)itemView.findViewById(R.id.likeButton);
        private ImageView likeFullButton= (ImageView)itemView.findViewById(R.id.likeFull);
        private TextView totalCountLike = itemView.findViewById(R.id.countLikes);


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = view.getContext();
            userImg = itemView.findViewById(R.id.feed_userImg);
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
            dateView = (TextView) itemView.findViewById(R.id.date);

            itemView.findViewById(R.id.putButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bool[getAdapterPosition()]) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            ViewHolder.this.itemViewPutButton.setVisibility(View.INVISIBLE);
                            ViewHolder.this.itemViewPutFinishButton.setVisibility(View.VISIBLE);
                            ViewHolder.this.itemViewTextView.setVisibility(View.GONE);
                            ViewHolder.this.itemViewPutText.setVisibility(View.VISIBLE);
                            ViewHolder.this.itemViewPutText.setCursorVisible(true);
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
                    String putMessage1 = ViewHolder.this.itemViewPutText.getText().toString();

                    if (!putMessage1.equals("클릭하여 글을 작성해주세요")){
                        updateCommunity(itemData.get(getAdapterPosition()).getId(), putMessage1);
                        ViewHolder.this.itemViewTextView.setText(putMessage1);
                    }
                    ViewHolder.this.itemViewPutButton.setVisibility(View.VISIBLE);
                    ViewHolder.this.itemViewPutFinishButton.setVisibility(View.INVISIBLE);
                    ViewHolder.this.itemViewPutText.setVisibility(View.GONE);
                    ViewHolder.this.itemViewTextView.setVisibility(View.VISIBLE);
                }
            });

            itemView.findViewById(R.id.feed_comment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadComments(itemData.get(getAdapterPosition()).getId(), getAdapterPosition());
                }
            });

            for (int i =0; i < itemData.size(); i++){
                if (!totalLike.containsKey(itemData.get(i).getId())){
                    totalLike.put(itemData.get(i).getId(), itemData.get(i).getLikeCount());
                }
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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

       view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_feedlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View otherView = inflater.inflate(R.layout.activity_maincomment, null, false);

        otherView.findViewById(R.id.post_save_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Feed item = itemData.get(position);
        holder.userName.setText(item.getNickName());

        try {
            URL url = new URL(item.getImg());
            InputStream inputStream = url.openConnection().getInputStream();
            bm = BitmapFactory.decodeStream(inputStream);
            holder.feedImg.setImageBitmap(bm);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String date = item.getCreateDate();
        date = date.substring(0, 10);
        holder.dateView.setText(date);

        if (item.getUserImg() == null){
            holder.userImg.setImageResource(R.drawable.catsby_logo);
        } else{
            try {
                URL url = new URL(item.getUserImg());
                InputStream inputStream = url.openConnection().getInputStream();
                bm = BitmapFactory.decodeStream(inputStream);
                holder.userImg.setImageBitmap(bm);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        holder.content.setText(item.getContent());
        if (likeCommunity.size() == 0 && !repeat){
            repeat = true;
            bowlCommunityService.getLikes(user.getUid()).enqueue(new Callback<BowlLikeList>() {
                @Override
                public void onResponse(Call<BowlLikeList> call, Response<BowlLikeList> response) {
                    if(response.isSuccessful()) {
                        BowlLikeList bowlResult = response.body();

                        assert bowlResult != null;
                        for (BowlLike bowlLike : bowlResult.getBowlLikes()) {
                            likeCommunity.put(bowlLike.getBowlCommunityId(), 0);
                            likeByCommunity.put(bowlLike.getBowlCommunityId(), bowlLike.getId());
                        }

                        if (likeCommunity.containsKey(itemData.get(position).getId())){
                            holder.likeButton.setVisibility(View.GONE);
                            holder.likeFullButton.setVisibility(View.VISIBLE);
                            if (holder.totalCountLike.getText().equals("0")){
                                holder.totalCountLike.setText("");
                            }
                        } else{
                            holder.likeButton.setVisibility(View.VISIBLE);
                            holder.likeFullButton.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<BowlLikeList> call, Throwable t) {

                }

            });
        }

        if (totalLike.containsKey(itemData.get(position).getId())){
            holder.totalCountLike.setText(totalLike.get(itemData.get(position).getId()).toString());
        }

        if (likeCommunity.containsKey(itemData.get(position).getId())){
            holder.likeButton.setVisibility(View.GONE);
            holder.likeFullButton.setVisibility(View.VISIBLE);
        } else{
            holder.likeButton.setVisibility(View.VISIBLE);
            holder.likeFullButton.setVisibility(View.GONE);
        }

        if (bool[position]) {
            holder.itemViewPutButton.setVisibility(View.VISIBLE);
            holder.itemViewDeleteButton.setVisibility(View.VISIBLE);
        } else{
            holder.itemViewPutButton.setVisibility(View.GONE);
            holder.itemViewDeleteButton.setVisibility(View.GONE);
        }
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
        bowlCommunityService.deleteLike(communityId, deleteLikeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                likeByCommunity.remove(communityId);
                likeCommunity.remove(communityId);
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
                likeCommunity.put(communityId, 0);
                likeByCommunity.put(communityId, response.body().getId());
                totalLike.put(communityId, totalLike.get(communityId)+1);
            }

            @Override
            public void onFailure(Call<BowlLikeResponse> call, Throwable t) {
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
                        BowlCommentUsingComment bowlCommentUsingComment = new BowlCommentUsingComment(tempComment.get(i).getId(), tempComment.get(i).getUser().getNickname(), tempComment.get(i).getContent(), tempComment.get(i).getCreateDate(), tempComment.get(i).getUser().getId(), tempComment.get(i).getUid(), (int) communityId);
                        parameterBowlCommentList.add(bowlCommentUsingComment);
                    }

                    MComment = parameterBowlCommentList;
                    intent.putExtra("comment", (Serializable) parameterBowlCommentList);

                    int cId = (int) communityId;
                    intent.putExtra("communityId", cId);
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
