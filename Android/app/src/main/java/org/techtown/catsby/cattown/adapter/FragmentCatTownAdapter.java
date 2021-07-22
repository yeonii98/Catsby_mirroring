package org.techtown.catsby.cattown.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.catsby.R;
import org.techtown.catsby.cattown.CatTownDetailActivity;
import org.techtown.catsby.cattown.model.Cat;

import java.util.ArrayList;

public class FragmentCatTownAdapter extends RecyclerView.Adapter<FragmentCatTownAdapter.ViewHolder> {
    private ArrayList<Cat> CatData = null;

    public FragmentCatTownAdapter(ArrayList<Cat> data) {
        CatData = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView townCatImage;
        TextView townCatName;
        TextView townHelpPeople;

        ViewHolder(View itemView) {
            super(itemView); // 뷰 객체에 대한 참조
            townCatImage = itemView.findViewById(R.id.towncatimage);
            townCatName = itemView.findViewById(R.id.towncatname);
            townHelpPeople = itemView.findViewById(R.id.towncathelppeople);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),CatTownDetailActivity.class);
                    intent.putExtra("id", townCatName.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragmentcattown_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cat cat = CatData.get(position);
        holder.townCatImage.setImageResource(cat.getCatPicture());
        holder.townCatName.setText(cat.getName());
        /*   error   */
        //holder.townHelpPeople.setText(cat.getHelpPeople());
    }

    @Override
    public int getItemCount() {
        return CatData.size();
    }

}




