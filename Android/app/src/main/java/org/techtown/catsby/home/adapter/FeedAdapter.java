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
import org.techtown.catsby.home.FragmentHome;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    Button postButton;
    EditText editText;
    ImageView feedCommentButton;
    Button deleteButton ;
    Button putButton ;
    Button putFinishButton;
    EditText putText;
    TextView textView;

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

            postButton = (Button)itemView.findViewById(R.id.post_save_button);
            editText = (EditText)itemView.findViewById(R.id.post_title_edit);
            feedCommentButton = (ImageView)itemView.findViewById(R.id.feed_comment);
            deleteButton = (Button)itemView.findViewById(R.id.deleteButton);
            putButton = (Button)itemView.findViewById(R.id.putButton);
            putFinishButton = (Button)itemView.findViewById(R.id.putFinishButton);
            putText = (EditText)itemView.findViewById(R.id.feed_content_EditText);
            textView = (TextView)itemView.findViewById(R.id.feed_content );

            putButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

            feedCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadComments(itemData.get(getAdapterPosition()).getId(), getAdapterPosition());

                }
            });

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

        byte[] blob = Base64.decode(item.getImg(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(blob,0, blob.length);
        holder.feedImg.setImageBitmap(bmp);
        holder.content.setText(item.getContent());

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
                //FragmentHome.bowlCommunityComment.remove(position);

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

    private void putGetUser(int position, String uid, int userId){
        userService.getUser(uid).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User result = response.body();

                    if (result.getId() == userId){
                        String putMessage = putText.getText().toString();
                        if (!putMessage.equals("클릭하여 글을 작성해주세요")){
                            updateCommunity(itemData.get(position).getId(), putMessage);
                            textView.setText(putMessage);
                        }
                        //putButton.setVisibility(View.VISIBLE);
                        //deleteButton.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(view.getContext(), "이것은 Toast 메시지입니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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

    private void loadComments(long communityId, int position) {
        bowlCommunityService.getComments(communityId).enqueue(new Callback<List<BowlComment>>() {
            @Override
            public void onResponse(Call<List<BowlComment>> call, Response<List<BowlComment>> response) {
                if(response.isSuccessful()){
                    List<BowlComment> bowlComments = response.body();

                    Context context = view.getContext();
                    Intent intent = new Intent(context, MaincommentActivity.class);

                    int idx = -1;
                    List<BowlComment> tempComment = bowlComments;

                    List<BowlCommentUsingComment> parameterBowlCommentList= new ArrayList<>();
                    for (int i =0; i < tempComment.size(); i++){
                        BowlCommentUsingComment bowlCommentUsingComment = new BowlCommentUsingComment(tempComment.get(i).getId(), tempComment.get(i).getUser().getNickname(), tempComment.get(i).getContent(), tempComment.get(i).getCreateDate());
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
