package org.techtown.catsby.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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
import com.soundcloud.android.crop.Crop;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlInfoTimeAdapter;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.service.BowlService;
import org.techtown.catsby.util.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BowlDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private GoogleMap googleMap;

    private TextView bowlName, bowlLocation;
    private Button completedFeed;
    private ImageView bowlImageView;

    private static final String TAG = "blackjin";

    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;

    RecyclerView recyclerView;
    BowlInfoTimeAdapter adapter;

    private NotificationService notificationService;
    private BowlService bowlService;

    private Boolean isCamera = false;

    String uid = FirebaseAuth.getInstance().getUid();

    Uri photoUri;
    File tempFile;
    Long bowlId;
    String name, address, bowlImage;
    Double latitude, longitude;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowl_detail);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("????????? ?????? ??????");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bowlName = (TextView) findViewById(R.id.txt_bowl_name);
        bowlLocation = (TextView) findViewById(R.id.txt_bowl_location);
        bowlImageView = findViewById(R.id.bowlimageView);

        Intent intent = getIntent();
        bowlId = intent.getLongExtra("id",0);
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        bowlImage= intent.getStringExtra("image");

        if (bowlImage != null) {
            try {
                URL url = new URL(bowlImage);
                InputStream inputStream = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                bowlImageView.setImageBitmap(bitmap);
                bowlImageView.setBackground(new ShapeDrawable(new OvalShape()));
                bowlImageView.setClipToOutline(true);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        bowlName.setText(name);
        bowlLocation.setText(address);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        notificationService = RetrofitClient.getNotificationService();
        completedFeed = (Button) findViewById(R.id.btn_completed_feed);
        completedFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getUid();

                new AlertDialog.Builder(BowlDetailActivity.this)
                        .setMessage("?????? ????????? ???????????????????")
                        .setPositiveButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendNotification(bowlId, uid);
                            }
                        })
                        .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();

            }
        });

        findViewById(R.id.btn_bowlpic_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(BowlDetailActivity.this)
                        .setMessage("????????? ?????????????????????????")
                        .setPositiveButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // ?????? ????????? ???????????? ????????? ?????? ???????????? ????????????.
                                if (isPermission) goToAlbum();
                                else
                                    Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();

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
            Toast.makeText(this, "?????? ???????????????.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if(tempFile.exists()) {

                    if(tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " ?????? ??????");
                        tempFile = null;
                    } else {
                        Log.e(TAG, "tempFile ?????? ??????");
                    }

                } else {
                    Log.e(TAG, "tempFile ???????????? ??????");
                }
            } else {
                Log.e(TAG, "tempFile is null");
            }

            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                Uri photoUri = data.getData();
                Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);
                cropImage(photoUri);
                break;
            }

            case Crop.REQUEST_CROP: {
                //File cropFile = new File(Crop.getOutput(data).getPath());
                setImage();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap = googleMap;

        LatLng place = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions();
        marker.position(place); //??????
        marker.title(name);

        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
    }

    private void goToAlbum() {
        isCamera = false;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /**
     *  Crop ??????
     */
    private void cropImage(Uri photoUri) {

        Log.d(TAG, "tempFile : " + tempFile);

        /**
         *  ??????????????? ????????? ???????????? tempFile??? ???????????? ?????? ??????????????????.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "????????? ?????? ??????! ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }

        //????????? ??? ????????? Uri
        Uri savingUri = Uri.fromFile(tempFile);
        Crop.of(photoUri, savingUri).asSquare().start(this);
    }


    /**
     * ?????? ??? ?????? ?????????
     */
    private File createImageFile() throws IOException {

        // ????????? ?????? ?????? ( blackJin_{??????}_ )
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // ???????????? ????????? ?????? ??????
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // ??? ?????? ??????
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }


    /**
     * tempFile ??? bitmap ?????? ?????? ??? ImageView ??? ????????????.
     */
    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();

        //????????? ?????? ??????
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        originalBm = ImageUtils.rotateBitmap(originalBm,orientation);

        bowlImageView.setImageBitmap(originalBm);

        File temp = getApplicationContext().getCacheDir();
        String fileName = uid + ".jpg";
        File image = new File(temp, fileName);
        image = bitmapConvertFile(image, originalBm);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), requestFile);

        updateImage(body);

        /**
         *  tempFile ?????? ??? null ????????? ????????? ?????????.
         *  (resultCode != RESULT_OK) ??? ??? tempFile ??? ???????????? ?????????
         *  ????????? ???????????? ?????? ?????? ?????? ?????? ?????? ????????? ???????????????.
         */
        tempFile = null;

    }


    private void updateImage(MultipartBody.Part body) {
        bowlService.updateImage(bowlId, FirebaseAuth.getInstance().getUid(), body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("BowlDetailActivity", "success update image");
                Toast.makeText(getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "???????????? ?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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

    private File bitmapConvertFile(File file, Bitmap bitmap) {

        OutputStream out = null;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}