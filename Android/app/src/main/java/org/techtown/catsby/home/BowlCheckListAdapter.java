package org.techtown.catsby.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.techtown.catsby.R;
import org.techtown.catsby.Writemain;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.service.BowlCommunityService;

import java.util.ArrayList;

public class BowlCheckListAdapter extends BaseAdapter {

    private ArrayList<BowlCheck> listViewItemList = new ArrayList<BowlCheck>() ;
    private ArrayList<Bowl> bowlData;
    private String conText;

    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public BowlCheckListAdapter(ArrayList<Bowl> bowlData, String conText) {
        this.bowlData = bowlData;
        this.conText = conText;
    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.write_main_bowl, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView textTextView = (TextView) convertView.findViewById(R.id.textView1) ;

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.bowlCheckBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println("compoundButton = " + compoundButton);
                Writemain.clickSave(position);

            }
        });

        BowlCheck listViewItem = listViewItemList.get(position);
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());


        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(Drawable icon, String text, int i) {
        BowlCheck item = new BowlCheck();

        item.setIcon(icon);
        item.setText(text);
        item.setPosition(i);

        listViewItemList.add(item);
    }
}
