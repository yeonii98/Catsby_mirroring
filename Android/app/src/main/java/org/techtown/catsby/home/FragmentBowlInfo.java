package org.techtown.catsby.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.techtown.catsby.R;

public class FragmentBowlInfo extends Fragment {
    private View view;
    private ImageView imageView;
    private TextView name,time,location;

    public static FragmentBowlInfo newInstance(){
        FragmentBowlInfo fragInfo = new FragmentBowlInfo();
        return fragInfo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bowlinfo,container,false);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        Bundle bundle = this.getArguments();
        name = (TextView)view.findViewById(R.id.name);

        if(bundle != null){
            name.setText(bundle.getString("name"));
        }else{
            name.setText("밥그릇~^^");
        }


        time = (TextView)view.findViewById(R.id.time);
        time.setText("10분 전");

        location = (TextView)view.findViewById(R.id.location);
        location.setText("남산타워");

        return view;
    }
}
