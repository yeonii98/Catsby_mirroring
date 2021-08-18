package org.techtown.catsby.home.adapter;

import android.text.Editable;
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
import org.techtown.catsby.retrofit.service.BowlCommunityService;

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
    View view;

    public FeedAdapter(ArrayList<Feed> itemData) {
        this.itemData = itemData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bowlImg;
        private TextView userName;
        private ImageView feedImg;
        //private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bowlImg = itemView.findViewById(R.id.feed_bowlImg);
            userName = itemView.findViewById(R.id.feed_username);
            feedImg = itemView.findViewById(R.id.feed_img);
            //content = itemView.findViewById(R.id.feed_content);
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

        Button postButton = (Button)view.findViewById(R.id.post_save_button);
        EditText editText = (EditText)view.findViewById(R.id.post_title_edit);

        postButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Context = editText.getText().toString();
                postComment(user.getUid(), itemData.get(position).getId(), Context);
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