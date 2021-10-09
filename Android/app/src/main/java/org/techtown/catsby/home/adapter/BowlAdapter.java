package org.techtown.catsby.home.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.home.BowlDetailActivity;
import org.techtown.catsby.retrofit.dto.BowlInfo;
import org.techtown.catsby.util.ImageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BowlAdapter extends RecyclerView.Adapter<BowlAdapter.ViewHolder> {
    int idx;
    private Bitmap bm;
    private ArrayList<BowlInfo> itemData;

    public BowlAdapter(ArrayList<BowlInfo> itemData){
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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_bowllist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BowlAdapter.ViewHolder holder, int position) {
        BowlInfo item = itemData.get(position);
        holder.text.setText(item.getName());

        if (item.getImage() == null){
            holder.image.setImageResource(R.drawable.catsby_logo);
        }
        else{
            try {
                URL url = new URL(item.getImage());
                InputStream inputStream = url.openConnection().getInputStream();
                bm = BitmapFactory.decodeStream(inputStream);
                holder.image.setImageBitmap(bm);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TextView name = v.findViewById(R.id.bowl_name);
                    Intent intent = new Intent(v.getContext(), BowlDetailActivity.class);
                    for (int i =0; i< itemData.size(); i++){
                        if (itemData.get(i).getName() == name.getText()){
                            idx = i;
                        }
                    }
                    BowlInfo item = itemData.get(idx);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("name", item.getName());
                    intent.putExtra("address", item.getAddress());
                    intent.putExtra("image", item.getImage());
                    intent.putExtra("latitude", item.getLatitude());
                    intent.putExtra("longitude", item.getLongitude());
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
