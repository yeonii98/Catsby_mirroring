package org.techtown.catsby.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommentUpdate;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.retrofit.service.BowlCommunityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;

public class BowlCommentAdapter extends RecyclerView.Adapter<BowlCommentAdapter.ViewHolder> {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<BowlCommentUsingComment> bowlCommentData;
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();

    Button commentDelete;
    Button commentUpdate;
    Button commentUpdateFinish;
    EditText textPost;

    View view;
    boolean[] bool;

    public BowlCommentAdapter(List<BowlCommentUsingComment> bowlCommentData) {
        this.bowlCommentData = bowlCommentData ;

        bool = new boolean[bowlCommentData.size()];
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText editText;
        TextView textView ;
        TextView nickNameView;
        Button commentDelete1 = (Button)itemView.findViewById(R.id.mainCmtDeleteBtn);
        Button commentUpdate1= (Button)itemView.findViewById(R.id.mainCmtUpdateBtn);
        Button commentUpdateFinish1= (Button)itemView.findViewById(R.id.mainCmtUpdateFinishBtn);


        EditText textPost = view.findViewById(R.id.post_text);

        ViewHolder(View itemView) {
            super(itemView) ;

            textView = view.findViewById(R.id.maincmtContent) ;
            nickNameView = view.findViewById(R.id.maincmtNickName);
            editText = view.findViewById(R.id.editCmtContent);

            for (int i =0; i < bowlCommentData.size(); i ++) {
                if (bool[i] == false){
                    if(bowlCommentData.get(i).getUid().equals(user.getUid())){
                        bool[i] = true;
                    }
                    else{
                        bool[i] = false;
                    }
                }
            }
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        view = inflater.inflate(R.layout.main_comment_list_item1, parent, false);
        commentDelete = (Button)view.findViewById(R.id.mainCmtDeleteBtn);
        commentUpdate = (Button)view.findViewById(R.id.mainCmtUpdateBtn);
        commentUpdateFinish = (Button)view.findViewById(R.id.mainCmtUpdateFinishBtn);

        BowlCommentAdapter.ViewHolder vh = new BowlCommentAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BowlCommentAdapter.ViewHolder holder, int position) {
        if (bowlCommentData.size() > 0 ){
            String text = bowlCommentData.get(position).getContent() ;
            String nickName = bowlCommentData.get(position).getNickname();

            holder.textView.setText(text);
            holder.nickNameView.setText(nickName);

            commentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteComment(position, bowlCommentData.get(position).getId());
                }
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (bool[position]) {
                holder.commentDelete1.setVisibility(VISIBLE);
                holder.commentUpdate1.setVisibility(VISIBLE);
            }
            else{
                holder.commentDelete1.setVisibility(INVISIBLE);
                holder.commentUpdate1.setVisibility(INVISIBLE);
            }

            commentUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.commentDelete1.setVisibility(View.GONE);
                    holder.commentUpdate1.setVisibility(View.GONE);
                    holder.commentUpdateFinish1.setVisibility(VISIBLE);
                    holder.textView.setVisibility(View.GONE);
                    holder.editText.setVisibility(VISIBLE);
                    holder.editText.setCursorVisible(true);
                }
            });

            commentUpdateFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String putMessage = holder.editText.getText().toString();
                    holder.textView.setText(putMessage);
                    putComment(bowlCommentData.get(position).getId(), putMessage);
                    holder.commentUpdateFinish1.setVisibility(View.GONE);
                    holder.commentUpdate1.setVisibility(VISIBLE);
                    holder.commentDelete1.setVisibility(VISIBLE);
                    holder.textView.setVisibility(VISIBLE);
                    holder.editText.setVisibility(View.GONE);
                }
            });
        }
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

    private void deleteComment(int position, int id) {
        bowlCommunityService.deleteComment(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                BowlCommentAdapter adapter = new BowlCommentAdapter(bowlCommentData);
                bowlCommentData.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRemoved(view.getVerticalScrollbarPosition());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void putComment(int id, String text){
        BowlCommentUpdate bowlCommentUpdate = new BowlCommentUpdate(text);
        bowlCommunityService.putComment(id, bowlCommentUpdate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return bowlCommentData.size() ;
    }

}