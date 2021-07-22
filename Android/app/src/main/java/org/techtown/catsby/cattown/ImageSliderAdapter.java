package org.techtown.catsby.cattown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {

    private Context context;
    private String[] catImages;

    public ImageSliderAdapter(Context context, String[] catImages) {
        this.context = context;
        this.catImages = catImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cattown_detail_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageSliderAdapter.ViewHolder holder, int position) {
        holder.bindSliderImage(catImages[position]);
    }

    @Override
    public int getItemCount() {
        return catImages.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cat);
        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(imageView);
        }
    }
}
