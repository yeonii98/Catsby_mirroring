package org.techtown.catsby.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlInfoTimeAdapter;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.dto.BowlImage;
import org.techtown.catsby.retrofit.service.BowlService;
import org.techtown.catsby.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BowlDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private GoogleMap mgoogleMap;

    private TextView bowlName, bowlLocation;
    private Button completedFeed;
    private ImageView bowlimageView;

    private static final String TAG = "blackjin";

    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;

    RecyclerView recyclerView;
    BowlInfoTimeAdapter adapter;

    private NotificationService notificationService;
    private BowlService bowlService;

    Uri photoUri;

    File tempFile;
    File image;

    Long bowlId;
    String name, address, bowlImage;
    Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowl_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("밥그릇 상세 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bowlName = (TextView) findViewById(R.id.txt_bowl_name);
        bowlLocation = (TextView) findViewById(R.id.txt_bowl_location);
        bowlimageView = findViewById(R.id.bowlimageView);

        Intent intent = getIntent();
        bowlId = intent.getLongExtra("id",0);
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        bowlImage = ImageUtils.byteArrayToBinaryString(intent.getByteArrayExtra("image"));
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        bowlName.setText(name);
        bowlLocation.setText(address);

        bowlimageView.setImageBitmap(ImageUtils.makeBitMap(bowlImage));
        bowlimageView.setBackground(new ShapeDrawable(new OvalShape()));
        bowlimageView.setClipToOutline(true);


        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        notificationService = RetrofitClient.getNotificationService();
        completedFeed = (Button) findViewById(R.id.btn_completed_feed);
        completedFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getUid();
                Log.e("FragmentInfo", uid);
                sendNotification(bowlId, uid);
            }
        });

        findViewById(R.id.btn_bowlpic_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if (isPermission) goToAlbum();
                else
                    Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        bowlService = RetrofitClient.getBowlService();
        adapter = new BowlInfoTimeAdapter(new ArrayList<>());
        recyclerView = (RecyclerView) findViewById(R.id.bowlinfo_time_recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        loadBowlFeedTime(bowlId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            photoUri = data.getData();
            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;
            try {

                String[] proj = {MediaStore.Images.Media.DATA};
                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                image = new File(cursor.getString(column_index));
                tempFile = new File(cursor.getString(column_index));
                //System.out.println("tempFile = " + tempFile);

                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mgoogleMap = googleMap;

        LatLng place = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions();
        marker.position(place); //좌표
        marker.title(name);

        mgoogleMap.addMarker(marker);
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
    }

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /**
     * 폴더 및 파일 만들기
     */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());
        return image;
    }

    /**
     * tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
     */
    private void setImage() {

        //회전 방지
        Glide.with(this).load(photoUri).circleCrop().into(bowlimageView);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());
        bowlimageView.setImageBitmap(originalBm);

        Bitmap img = ((BitmapDrawable) bowlimageView.getDrawable()).getBitmap();
        String image = "";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        byte[] byteArray = stream.toByteArray();
        image = ImageUtils.byteArrayToBinaryString(byteArray);
        updateImage(image);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }


    private void updateImage(String image) {
        bowlService.updateImage(bowlId, FirebaseAuth.getInstance().getUid(), new BowlImage(image)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("BowlDetailActivity", "success update image");

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("BowlDetailActivity", "Update Image Fail" + t.getMessage());

            }
        });
    }

    private void sendNotification(Long bowlId, String uid) {
        notificationService.sendNotification(bowlId, uid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("FragmentBowlInfo", "send Notification " + response.body().getResponse());
                    Toast.makeText(getApplicationContext(), "밥그릇에 급여 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    loadBowlFeedTime(bowlId);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FragmentBowlInfo", "error send notification from API");
            }
        });
    }

    private void loadBowlFeedTime(Long bowlId) {
        bowlService.getBowlFeed(bowlId).enqueue(new Callback<BowlFeedList>() {

            @Override
            public void onResponse(Call<BowlFeedList> call, Response<BowlFeedList> response) {
                if (response.isSuccessful()) {
                    adapter.loadBowlFeedTime(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BowlFeedList> call, Throwable t) {
                Log.e("BowlDetailActivity", "Response Fail" + t.getMessage());

            }
        });
    }
}