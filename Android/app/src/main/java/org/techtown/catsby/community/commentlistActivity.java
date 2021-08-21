package org.techtown.catsby.community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.dto.BowlComment;

import java.util.List;

public class commentlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentlist);

        Intent intent = getIntent();
        List<BowlComment> item = (List<BowlComment>) intent.getSerializableExtra("comment");

    }
}