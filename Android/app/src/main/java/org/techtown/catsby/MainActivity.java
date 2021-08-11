package org.techtown.catsby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.catsby.community.FragmentCommunity;
import org.techtown.catsby.cattown.FragmentCatTown;
import org.techtown.catsby.home.FragmentHome;
import org.techtown.catsby.notification.NotificationActivity;
import org.techtown.catsby.setting.FragmentSetting;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final FragmentHome fragmenthome = new FragmentHome();
    private final FragmentCatTown fragmentcattown = new FragmentCatTown();
    private final FragmentCommunity fragmentcommunity = new FragmentCommunity();
    private final FragmentSetting fragmentsetting = new FragmentSetting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //permission
        AutoPermissions.Companion.loadAllPermissions(this, 101);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmenthome).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notice:
                Intent notificateionIntent = new Intent(this, NotificationActivity.class);
                startActivity(notificateionIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch(menuItem.getItemId())
            {
                case R.id.iconHome:
                    transaction.replace(R.id.frameLayout, fragmenthome).commitAllowingStateLoss();
                    break;
                case R.id.iconCommunity:
                    transaction.replace(R.id.frameLayout, fragmentcommunity).commitAllowingStateLoss();
                    break;
                case R.id.iconCatTown:
                    transaction.replace(R.id.frameLayout, fragmentcattown).commitAllowingStateLoss();
                    break;
                case R.id.iconSetting:
                    transaction.replace(R.id.frameLayout, fragmentsetting).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    //사용자 권한
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
    }

    @Override
    public void onGranted(int i, String[] strings) {
    }

}