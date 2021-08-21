package org.techtown.catsby.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import org.techtown.catsby.R;

public class BowlCheckableLayout extends LinearLayout implements Checkable {

    public BowlCheckableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.bowlCheckBox) ;
        return cb.isChecked() ;
    }

    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.bowlCheckBox) ;
        setChecked(cb.isChecked() ? false : true) ;
        // setChecked(mIsChecked ? false : true) ;
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.bowlCheckBox) ;
/*
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println("compoundButton = " + compoundButton);
                System.out.println("compoundButton = " + compoundButton);
            }
        });*/


        //System.out.println("cb = " + cb.getPos);

        if (cb.isChecked() != checked) {
            cb.setChecked(checked) ;
        }

    }
}