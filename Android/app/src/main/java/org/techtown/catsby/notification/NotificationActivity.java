package org.techtown.catsby.notification;

import android.os.Bundle;
import android.view.MenuItem;

import org.techtown.catsby.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<NotificationItemData> dataList = new ArrayList<>();
    int[] cat = {R.drawable.pic_001, R.drawable.pic_002, R.drawable.pic_003,
            R.drawable.pic_004, R.drawable.pic_005, R.drawable.pic_001,
            R.drawable.pic_002, R.drawable.pic_003, R.drawable.pic_004,
            R.drawable.pic_005, R.drawable.pic_001, R.drawable.pic_002, R.drawable.pic_003,
            R.drawable.pic_004, R.drawable.pic_005,
            R.drawable.pic_001, R.drawable.pic_002, R.drawable.pic_003,
            R.drawable.pic_004, R.drawable.pic_005, R.drawable.pic_001, R.drawable.pic_002, R.drawable.pic_003,
            R.drawable.pic_004, R.drawable.pic_005, R.drawable.pic_001, R.drawable.pic_002, R.drawable.pic_003,
            R.drawable.pic_004, R.drawable.pic_005,
            R.drawable.pic_001, R.drawable.pic_002, R.drawable.pic_003,
            R.drawable.pic_004, R.drawable.pic_005,};

    final NotificationAdapter adapter = new NotificationAdapter(dataList);
    static int i=0;

    //    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        for (int i=0; i<10; i++) {
            dataList.add(new NotificationItemData(cat[i], "@냐옹님이 회원님의 게시글을 좋아합니다.",
                    String.format("%d분 전", i)));
        }

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }

}
