package org.techtown.catsby.home.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.home.BowlActivity;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.retrofit.dto.BowlDto;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BowlAdapter extends RecyclerView.Adapter<BowlAdapter.ViewHolder> {

    private ArrayList<Bowl> itemData;

    public BowlAdapter(ArrayList<Bowl> itemData){
        this.itemData = itemData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView text;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.bowl_img);
            text = itemView.findViewById(R.id.bowl_name);
        }
    }

    public interface BowlAdapterClickListener{
        void onItemClicked(int position);
    }

    private BowlAdapterClickListener bListener;

    public void setOnClickListener(BowlAdapterClickListener listener){
        this.bListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_bowllist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BowlAdapter.ViewHolder holder, int position) {

        Bowl item = itemData.get(position);
        //holder.image.setImageResource(item.getImage());
        holder.text.setText(item.getName());

        if (bListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TextView name = v.findViewById(R.id.bowl_name);
                    Intent intent = new Intent(v.getContext(), BowlActivity.class);

                    for(int i =0; i < itemData.size(); i++){
                        System.out.println("???????????????????????"+itemData.get(i).getName());
                    }

                    System.out.println("name.getText()!!!!!!!!!!!!!!!!!!!! = " + name.getText());

                    intent.putExtra("name", name.getText());

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }
}
