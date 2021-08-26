package org.techtown.catsby.cattown.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
    private ArrayList<Cat> catdata;

    public FragmentCatTownAdapter(ArrayList<Cat> catdata) {
        this.catdata = catdata;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView townCatImage;
        TextView townCatName;
        TextView townCatId;
        TextView townCatGen;
        TextView townCatLoc;


        ViewHolder(View itemView) {
            super(itemView); // 뷰 객체에 대한 참조
            townCatImage = itemView.findViewById(R.id.towncatimage);
            townCatName = itemView.findViewById(R.id.towncatname);
            townCatGen = itemView.findViewById(R.id.towncatgen);
            townCatLoc = itemView.findViewById(R.id.towncatloc);
            townCatId = itemView.findViewById(R.id.towncatid);
            //townHelpPeople = itemView.findViewById(R.id.towncathelppeople);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),CatTownDetailActivity.class);
                    intent.putExtra("linkedid",townCatId.getText());
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
        Cat cat = catdata.get(position);

        //String simage = catdata.get(position).getCatPicture();
       // System.out.println("simage="+simage);
        //Bitmap s2 = StringToBitmap(simage);
        //System.out.println(s2);
        //이미지 보류
        holder.townCatImage.setImageBitmap(cat.getCatPicture());
        holder.townCatName.setText(cat.getName());
        holder.townCatId.setText(cat.getCat_id());
        holder.townCatGen.setText(cat.getCatgen());
        holder.townCatLoc.setText(cat.getCatloc());
        /*   error   */
        //holder.townHelpPeople.setText(cat.getHelpPeople());
    }

    //지연님 코드
    public Bitmap makeBitMap(String s){
        int idx = s.indexOf("=");
        byte[] b = binaryStringToByteArray(s.substring(idx+1));
        Bitmap bm = BitmapFactory.decodeByteArray(b,0,b.length);
        return bm;
    }

    public byte[] binaryStringToByteArray(String s){
        int count=s.length()/8;
        byte[] b=new byte[count];
        for(int i=1; i<count; ++i){
            String t=s.substring((i-1)*8, i*8);
            b[i-1]=binaryStringToByte(t);
        }
        return b;
    }

    public byte binaryStringToByte(String s){
        byte ret=0, total=0;
        for(int i=0; i<8; ++i){
            ret = (s.charAt(7-i)=='1') ? (byte)(1 << i) : 0;
            total = (byte) (ret|total);
        }
        return total;
    }
    //이진형 String to Bitmap
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return catdata.size();
    }

    /*
    public void addItem(int picture, String catName, int helper) {
        Cat cat = new Cat(null,null,0);
        //cat.setCatPicture(picture);
        cat.setName(catName);
        cat.setHelpPeople(helper);
    }

     */

    public void addItem(Cat cat) { catdata.add(cat); }

}




