package org.techtown.catsby.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.home.model.Feed;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<Feed> itemData;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragmenthome_feedlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {

        Feed item = itemData.get(position);
        holder.bowlImg.setImageResource(item.getBowlImg());
        holder.userName.setText(item.getUserName());
        holder.feedImg.setImageResource(item.getImg());
        holder.content.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }
}
