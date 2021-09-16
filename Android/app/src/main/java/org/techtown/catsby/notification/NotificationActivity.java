package org.techtown.catsby.notification;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.notification.data.model.NotificationList;
import org.techtown.catsby.notification.data.service.NotificationService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity {

    private NotificationAdapter adapter;
    private RecyclerView recyclerView;
    private NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("알림");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new NotificationAdapter(new ArrayList<>());
        notificationService = RetrofitClient.getNotificationService();
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            loadNotifications(user.getUid());
        }
    }

    private void loadNotifications(String uid) {
        notificationService.getNotifications(uid, 0).enqueue(new Callback<NotificationList>() {
            @Override
            public void onResponse(Call<NotificationList> call, Response<NotificationList> response) {
                if(response.isSuccessful()) {
                    adapter.updateNotifications(response.body().getNotificationList());
                    Log.d("NotificationActivity", "Get Notification List from API");
                }
            }

            @Override
            public void onFailure(Call<NotificationList> call, Throwable t) {
                Log.d("NotificationActivity", "error loading from API");
            }
        });
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

}
