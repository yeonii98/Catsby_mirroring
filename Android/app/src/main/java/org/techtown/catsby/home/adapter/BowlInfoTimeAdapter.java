package org.techtown.catsby.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.home.model.BowlInfoTimeItem;
import org.techtown.catsby.retrofit.dto.BowlFeed;

import java.util.List;

public class BowlInfoTimeAdapter extends RecyclerView.Adapter<BowlInfoTimeAdapter.ViewHolder> {

    private List<BowlFeed> itemData;

    public BowlInfoTimeAdapter(List<BowlFeed> itemData) {
        this.itemData = itemData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView formatTime, time;

        ViewHolder(View itemView) {
            super(itemView); // 뷰 객체에 대한 참조

            formatTime = itemView.findViewById(R.id.txt_bowl_formatTime);
            time = itemView.findViewById(R.id.txt_bowl_time);
        }
    }

    @NonNull
    @NotNull
    @Override
    public BowlInfoTimeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.bowlinfo_time_item_list, parent, false);
        BowlInfoTimeAdapter.ViewHolder vh = new BowlInfoTimeAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BowlInfoTimeAdapter.ViewHolder holder, int position) {
        BowlFeed item = itemData.get(position);
        holder.formatTime.setText(item.getFormatTime());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public void loadBowlFeedTime(List<BowlFeed> bowlFeeds) {
        itemData = bowlFeeds;
        notifyDataSetChanged();
    }
}