package org.techtown.catsby.home.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;
import org.techtown.catsby.home.BowlDetailActivity;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.retrofit.dto.BowlDetail;
import org.techtown.catsby.retrofit.dto.BowlInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BowlAdapter extends RecyclerView.Adapter<BowlAdapter.ViewHolder> {

    private ArrayList<BowlInfo> itemData;
    int idx;
    private Bitmap bm = null;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_bowllist, parent, false);
        return new ViewHolder(view);
    }

    public byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    public byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    public Bitmap makeBitMap(String s) {
        int idx = s.indexOf("=");
        byte[] b = binaryStringToByteArray(s.substring(idx + 1));
        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bm;
    }

    @Override
    public void onBindViewHolder(@NonNull BowlAdapter.ViewHolder holder, int position) {
        BowlInfo item = itemData.get(position);
        holder.text.setText(item.getName());


        if (item.getImage() == null){
            holder.image.setImageResource(R.drawable.catsby_logo);
        }
        else{
             bm = makeBitMap(item.getImage());
             holder.image.setImageBitmap(bm);
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
