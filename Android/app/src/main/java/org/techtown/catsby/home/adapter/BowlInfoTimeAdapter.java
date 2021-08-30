package org.techtown.catsby.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.home.model.BowlInfoTimeItem;
import org.techtown.catsby.notification.data.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class BowlInfoTimeAdapter extends RecyclerView.Adapter<BowlInfoTimeAdapter.ViewHolder> {

    private ArrayList<BowlInfoTimeItem> itemData = new ArrayList<>();

    public BowlInfoTimeAdapter(ArrayList<BowlInfoTimeItem> itemData) {
        this.itemData = itemData;
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
        BowlInfoTimeItem item = itemData.get(position);

        System.out.println("item = !!!!!!!!!!!!!" + item.getTimeItem());

        holder.bowlTime.setText(item.getTimeItem());
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bowlTime;

        ViewHolder(View itemView) {
            super(itemView); // 뷰 객체에 대한 참조

            bowlTime = itemView.findViewById(R.id.bowl_time_text);
        }
    }

    public void updateNotifications() {
        itemData.add(new BowlInfoTimeItem("1일전"));
        itemData.add(new BowlInfoTimeItem("2일전"));
        itemData.add(new BowlInfoTimeItem("3일전"));
        itemData.add(new BowlInfoTimeItem("4일전"));

        notifyDataSetChanged();
    }

}
