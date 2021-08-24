package org.techtown.catsby.cattown.addCat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.techtown.catsby.R;
import org.techtown.catsby.cattown.FragmentCatTown;
import org.techtown.catsby.retrofit.dto.CatProfile;
import org.techtown.catsby.retrofit.service.CatService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddCatActivity extends AppCompatActivity{
    String imgName = "cat1.png";    // 저장할 이미지 이름
    TextView textView;

    private Button saveButton;
    private Button button;
    private Button imageButton;
    private EditText edtName;
    private EditText edtHealth;
    private EditText edtLoc;
    private RadioGroup rbgender;
    private RadioButton rbfemale;
    private RadioButton rbmale;
    private RadioGroup rbisspayed;
    private RadioButton rbspayed;
    private RadioButton rbnospayed;
    private EditText edtcontent;
    private String message;
    private String location;
    private TextView imageuri;
    byte imageArray [];
    Bitmap imgBitmap;
    Bitmap bitmap;

    //이미지 업로드
    public void imageUpload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    //이미지 선택 시 버튼 없애고 텍스트뷰로 경로 보여주기
                    imageButton.setVisibility(View.GONE);
                    imageuri.setVisibility(View.VISIBLE);
                    imageuri.setText(fileUri.toString());

                    InputStream instream = resolver.openInputStream(fileUri);
                    imgBitmap = BitmapFactory.decodeStream(instream);
                    //데이터베이스에 이미지 저장 : Data URI -> Byte Array -> Blob
                    //imageArray = bitmapToByteArray(imgBitmap);
                    //System.out.println(imageArray);
                    byte[] inputData = getBytes(instream);
                    imageArray = inputData;


                    System.out.println("@imgBitmap = " + imgBitmap);
                    instream.close();
                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                    Toast.makeText(getApplicationContext(), "이미지 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "이미지 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
        return bytesResult;
    }

    public byte[] bitmapToByteArray( Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }


    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getCacheDir(), imgName);    // 파일 경로와 이름
        try {
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // 스트림에 비트맵을 저장하기
            out.close();
            Toast.makeText(getApplicationContext(), "이미지 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "이미지 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageButton = findViewById(R.id.buttonCatImage);
        saveButton = findViewById(R.id.buttonCatSave);
        edtName = findViewById(R.id.editCatName);
        edtHealth = findViewById(R.id.editCatHealth);
        edtLoc = findViewById(R.id.editloc);
        rbfemale = findViewById(R.id.rb_female);
        rbmale = findViewById(R.id.rb_male);
        rbspayed = findViewById(R.id.rb_spayed);
        rbnospayed = findViewById(R.id.rb_nospayed);
        edtcontent = findViewById(R.id.editCatExplanation);
        imageuri = findViewById(R.id.tvCatImageUri);


        //사진 등록하기 버튼
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUpload(view);
            }
        });


        //레트로핏
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catname = edtName.getText().toString();
                String cathealth = edtHealth.getText().toString();
                String catloc = edtLoc.getText().toString();
                Boolean catgender = true;
                if(rbfemale.isChecked()==true){
                    catgender = true;
                }else{
                    catgender = false;
                }
                Boolean catspayed = true;
                if(rbspayed.isChecked()==true){
                    catspayed = true;
                }else {
                    catspayed = false;
                }
                String catcontent = edtcontent.getText().toString();
                byte[] catimage = imageArray;

                System.out.println("catimage ="+imageArray);

                CatService retrofitService = retrofit.create(CatService.class);
                Call<CatProfile> call = retrofitService.setPost(
                        catname,cathealth,catloc, catgender, catimage, catcontent, catspayed);
                call.enqueue(new Callback<CatProfile>(){

                    @Override
                    public void onResponse(Call<CatProfile> call, Response<CatProfile> response) {
                        if(response.isSuccessful()) {
                            System.out.println("성공");
                            CatProfile cat = response.body();
                        }
                        else {
                            //System.out.println("실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<CatProfile> call, Throwable t) {
                        //System.out.println("통신 실패");
                    }
                });
            }
        });

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /* 고양이 위치 찾기 (기능 삭제)
    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                message = "현재 위치\n"+ "Latitude : " + latitude + "\nLongitude:" + longitude;

                //tvloc.setText(message);


            }

            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance, gpsListener);

            Toast.makeText(getApplicationContext(), "내 위치확인",
                    Toast.LENGTH_SHORT).show();

        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            System.out.println("longitude = " + longitude);
            System.out.println("latitude = " + latitude);
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

     */


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
