package org.techtown.catsby.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.community.CommentlistActivity;
import org.techtown.catsby.home.FragmentHome;
import org.techtown.catsby.home.model.Feed;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommunityPost;
import org.techtown.catsby.retrofit.dto.BowlCommunityUpdatePost;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.UserService;

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
    UserService userService = RetrofitClient.getUser();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    View view;

    public FeedAdapter(ArrayList<Feed> itemData) {
        this.itemData = itemData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bowlImg;
        private TextView userName;
        private ImageView feedImg;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bowlImg = itemView.findViewById(R.id.feed_bowlImg);
            userName = itemView.findViewById(R.id.feed_username);
            feedImg = itemView.findViewById(R.id.feed_img);
            content = itemView.findViewById(R.id.feed_content);
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
        holder.bowlImg.setImageResource(item.getBowlImg());
        holder.userName.setText(item.getNickName());
        holder.feedImg.setImageResource(item.getImg());
        holder.content.setText(item.getContent());

        Button postButton = (Button)view.findViewById(R.id.post_save_button);
        EditText editText = (EditText)view.findViewById(R.id.post_title_edit);
        ImageView feedButton = (ImageView)view.findViewById(R.id.feed_comment);
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        Button putButton = (Button)view.findViewById(R.id.putButton);
        Button putFinishButton = (Button)view.findViewById(R.id.putFinishButton);
        EditText putText = (EditText)view.findViewById(R.id.feed_content_EditText);
        TextView textView = (TextView)view.findViewById(R.id.feed_content );

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser(position, itemData.get(position).getUserId(), user.getUid());
            }
        });

        postButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Context = editText.getText().toString();
                postComment(user.getUid(), itemData.get(position).getId(), Context);
            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = view.getContext();
                Intent intent = new Intent(context, CommentlistActivity.class);
                System.out.println("intent = !!!!!!!!!!!!!!!!! " + itemData.size());

                List<BowlComment> tempComment = itemData.get(position).getBowlComments().get(position);

                System.out.println("position = " + position);
                System.out.println("tempComment = @@@@@@@@@@@@@ " + tempComment.size());

                intent.putExtra("comment", (Serializable) tempComment);

                context.startActivity(intent);
            }
        });

        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putButton.setVisibility(View.INVISIBLE);
                putFinishButton.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                putText.setVisibility(View.VISIBLE);
                putText.setCursorVisible(true);

            }
        });

        putFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String putMessage = putText.getText().toString();

                if (!putMessage.equals("클릭하여 글을 작성해주세요")){
                    updateCommunity(itemData.get(position).getId(), putMessage);
                    textView.setText(putMessage);
                }

                putButton.setVisibility(View.VISIBLE);
                putFinishButton.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                putText.setVisibility(View.INVISIBLE);

                System.out.println("putMessage = " + putMessage);
            }
        });
    }

    private void updateCommunity(int communityId, String changeTest) {
        BowlCommunityUpdatePost bowlCommunityUpdatePost = new BowlCommunityUpdatePost(changeTest);
        bowlCommunityService.updateCommunity(communityId, bowlCommunityUpdatePost).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(" success ");
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
                FragmentHome.bowlCommunityContext.remove(position);
                FragmentHome.bowlCommunityId.remove(position);
                FragmentHome.bowlCommunityUser.remove(position);
                FragmentHome.bowlCommunityUserId.remove(position);
                FragmentHome.bowlCommunityComment.remove(position);
                itemData.remove(itemData.get(position));
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

    private void getUser(int position, int communityUserId, String uid) {
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

    /* Bowl_Comment Post*/
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

    @Override
    public int getItemCount() {
        return itemData.size();
    }
}