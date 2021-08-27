package org.techtown.catsby.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.FeedAdapter;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentUpdate;
import org.techtown.catsby.retrofit.dto.BowlCommentUpdatePost;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCommentAdapter extends RecyclerView.Adapter<MainCommentAdapter.ViewHolder> {

    List<BowlCommentUsingComment> mData;
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    Button commentDelete;
    Button commentUpdate;
    Button commentUpdateFinish;
    View view;

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText editText;
        TextView textView ;
        TextView nickNameView;

        ViewHolder(View itemView) {
            super(itemView) ;
            textView = view.findViewById(R.id.maincmtContent) ;
            nickNameView = view.findViewById(R.id.maincmtNickName);
            editText = view.findViewById(R.id.editCmtContent);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MainCommentAdapter(List<BowlCommentUsingComment> list) {
        this.mData = list ;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        view = inflater.inflate(R.layout.main_comment_list_item, parent, false) ;
        commentDelete = (Button)view.findViewById(R.id.mainCmtDeleteBtn);
        commentUpdate = (Button)view.findViewById(R.id.mainCmtUpdateBtn);
        commentUpdateFinish = (Button)view.findViewById(R.id.mainCmtUpdateFinishBtn);

        MainCommentAdapter.ViewHolder vh = new MainCommentAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainCommentAdapter.ViewHolder holder, int position) {
        if (mData.size() > 0 ){
            String text = mData.get(position).getContent() ;
            String nickName = mData.get(position).getNickname();

            holder.textView.setText(text);
            holder.nickNameView.setText(nickName);

            commentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteComment(position, mData.get(position).getId());
                }
            });

            commentUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    commentUpdate.setVisibility(View.GONE);
                    commentDelete.setVisibility(View.GONE);
                    commentUpdateFinish.setVisibility(View.VISIBLE);
                    holder.textView.setVisibility(View.GONE);
                    holder.editText.setVisibility(View.VISIBLE);
                    holder.editText.setCursorVisible(true);
                }
            });

            commentUpdateFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String putMessage = holder.editText.getText().toString();
                    holder.textView.setText(putMessage);
                    putComment(mData.get(position).getId(), putMessage);
                    commentUpdateFinish.setVisibility(View.GONE);
                    commentUpdate.setVisibility(View.VISIBLE);
                    commentDelete.setVisibility(View.VISIBLE);
                    holder.textView.setVisibility(View.VISIBLE);
                    holder.editText.setVisibility(View.GONE);
                }
            });
        }
    }

    private void deleteComment(int position, int id) {
        bowlCommunityService.deleteComment(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                MainCommentAdapter adapter = new MainCommentAdapter(mData);
                mData.remove(position);
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
        return mData.size() ;
    }

}