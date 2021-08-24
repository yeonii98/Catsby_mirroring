package org.techtown.catsby.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.FeedAdapter;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentUsingComment;
import org.techtown.catsby.retrofit.service.BowlCommunityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCommentAdapter extends RecyclerView.Adapter<MainCommentAdapter.ViewHolder> {

    List<BowlCommentUsingComment> mData;
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    Button commentDelete;
    Button commentUpdate;
    View view;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentView ;
        TextView nickNameView;

        ViewHolder(View itemView) {
            super(itemView) ;
            contentView = itemView.findViewById(R.id.maincmtContent) ;
            nickNameView = itemView.findViewById(R.id.maincmtNickName);
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

        MainCommentAdapter.ViewHolder vh = new MainCommentAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainCommentAdapter.ViewHolder holder, int position) {
        if (mData.size() > 0 ){
            String text = mData.get(position).getContent() ;
            String nickName = mData.get(position).getNickname();

            holder.contentView.setText(text) ;
            holder.nickNameView.setText(nickName);

            commentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteComment(position, mData.get(position).getId());
                }
            });
        }
    }

    private void deleteComment(int position, int id) {
        bowlCommunityService.deleteComment(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(" success ");

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

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}